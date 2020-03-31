use std::io::{ErrorKind, Read, Write};
use std::net::TcpListener;
use std::sync::mpsc;
use std::thread;

//ip and port
const LOCAL: &str = "127.0.0.1:6000";
//buffer size of messages
const MSG_SIZE: usize = 32;

//allows the thread to sleep while its not reciving messages
fn sleep(){
    thread::sleep(::std::time::Duration::from_millis(100));
}

fn main(){
    let server = TcpListener::bind(LOCAL).expect("Listener failed to build");
    
    //non-blocking mode allows server to constantly check for messages
    server.set_nonblocking(true).expect("failed to initialize non-blocking mode");

    //muteable vector to store and allow for multiple clients to connect at once
    let mut clients = vec![];
    //init channel - assiging string type - sending a bunch of strings (will be shorts)
    let (tx, rx) = mpsc::channel::<String>();
    loop{
        //socket = tcp stream connecting
        //addr = actual socket address
        if let Ok((mut socket, addr)) = server.accept() {
            println!("Client {} connected", addr);
            
            //socket is being cloned here so it can be pushed into our thread
            let tx = tx.clone();
            clients.push(socket.try_clone().expect("Failed to clone client"));
            
            thread::spawn(move || loop {
                let mut buff = vec![0; MSG_SIZE];

                //reads our message into our buffer
                match socket.read_exact(&mut buff){
                    Ok(_) => {
                        //convert message into an iterator 
                        //take all characters that are not whitespace and place inside vector
                        let msg = buff.into_iter().take_while(|&x| x != 0).collect::<Vec<_>>();
                        //convert slice of strings into an actual string
                        let msg = String::from_utf8(msg).expect("Invalid utf8 message");
                        
                        //debug flag added
                        println!("{}: {:?}", addr, msg);
                        //send our message from our transmitter to our reciver
                        tx.send(msg).expect("failed to send msg to rx");
                    },
                    Err(ref err) if err.kind() == ErrorKind::WouldBlock => (),
                    Err(_) => {
                        println!("closing connection with: {}", addr);
                        break;
                    }
                }
                //thread sleep
                sleep();
            });
        }
        // when server recives a message
        if let Ok(msg) = rx.try_recv(){
            clients = clients.into_iter().filter_map(|mut client|{
                let mut buff = msg.clone().into_bytes();
                buff.resize(MSG_SIZE,0);

                //send whole buffer back to client
                client.write_all(&buff).map(|_| client).ok()
            }).collect::<Vec<_>>();
        }
        //thread sleep
        sleep();
    }
} 
