use std::io::{ErrorKind, Read, Write};
use std::net::TcpListener;
use std::sync::mpsc;
use std::thread;

//ip and port
const LOCAL &str = "127.0.0.1:6000";
//buffer size of messages
const MSG_SIZE: usize = 32;

fn main(){
    let server = TcpListener::bind(LOCAL).except("Listener failed to build");
    
    //non-blocking mode allows server to constantly check for messages
    server.set_nonblocking(true).except("failed to initialize non-blocking mode");

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
            clients.push(socket.try_clone().except("Failed to clone client"));
            
            thread::spawn(move || loop {
                let mut buff = vec![0; MSG_SIZE];

                //reads our message into our buffer
                match socket.read_exact(&mut buff){
                    Ok(_) => {
                        //convert message into an iterator 
                        //take all characters that are not whitespace and place inside vector
                        let msg = buff.into_iter().take_while(|&x| x != 0).collect::<Vec<_>>();
                        //convert slice of strings into an actual string
                        let msg = String::from_utf8(msg).except("Invalid utf8 message");
                        
                        //debug flag added
                        println!("{}: {:?}", addr, msg);
                        //send our message from our transmitter to our reciver
                        tx.send(msg).except("failed to send msg to rx");
                    }
                }
            })
        }
    }
}
