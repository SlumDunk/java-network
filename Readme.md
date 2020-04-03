## day01
    OSI model
    
        application level
            An user interface between web services and end users
            TFTP, HTTP, SMTP, FTP, DNS, Telnet
            
        presentation layer
            Transform the information provided by the application layer into a form that can be understood together
            
        session level
        
        transportation layer
            Define the protocol and port number for transmitting data, as well as flow control and error checking
            TCP, UDP
            
        network layer
            router and relay
            multiple connections use one data link
            IP, ICMP, RIP, OSPF, BGP, IGMP
            
        data-link layer
            establish, remove data link, check data errors
            SLIP, CSLIP,, PPP, ARP, RARP, MTU
            
        physical layer
            provide physical Network channel for data transmission
            ISO02110, IEEE802, IEEE802.2
    
    TCP-IP model
        application layer
        
        transportation layer
        
        network layer
        
        link layer
    
    
    TCP
        Connection-Oriented, reliable (sequence+acknowledgement+resend) 
        
        three-times handshake for building connection,
                
            1.SYN=1, seq=J (client to server)
                SYN is flag, represents synchronize the connection
                
            2.SYN=1, ACK=1, ack=J+1, seq=K (server to client)
                
            3.ACK=1, ack=K+1 (client to server)
        
        four-times for removing connection
            1. FIN=1, seq=u (client to server)
            
            2. ACK=1, seq=v, ack=u+1 (server to client)
            
            3. FIN=1, ACK=1, seq=w, ack=u+1 (server to client)
            
            4. ACK=1, seq=u+1, ack=w+1 (client to server)
        
    UDP
        NoConnection-Oriented, unreliable
        
        NTP, DNS, broadcast
        
    Port
    
    Data Package
    
        Data Frame
            data-link layer
            
        Data Slice
            IP layer
        
        Data Segment
            Transformation layer
        
        Message
            Application layer
    
    The progress of HTTP request:
    1. set TCP connection (include DNS search)
    2. client send request command to server
    3. client send request header
    4. client send request body
    5. server response http/1.1 200 OK
    6. server return the response header
    7. server send data to client
    8. server close the TCP connection (keep-alive)
    
    Socket
        the middle software layer between application layer and TCP/IP, an interface
        
    Short connection
    
    Long connection
    
    5 I/O models
        blocking I/O (client side popular)
            copy data from network card to kernel space, then copy from kernel space to user space
        
        nonblocking I/O (cpu cost)
        
        I/O multiplexing (select, poll, and epoll) -- Linux server side popular
        
        Signal Driven I/O (SIGIO) 
        
        asynchronous I/O -- windows IOCP
        
        
        
    
     
    how to differentiate sockets?
        protocol + source ip address + source port + destination ip address + destination port
        
## day 02
    BIO
        ServerSocket
        Socket
    
    AIO
        AsynchronousServerSocketChannel
        AsynchronousSocketChannel
        CompletionHandler
        Buffer
    
    NIO
        Reactor Mode
            Single Reactor
            Worker Thread Pools
            Multiple Reactors      
            
         SocketChannel
         
         ServerSocketChannel
         
         Datagram
         
         Selector
         
         SelectionKey
    
    Buffer
        Write mode:
            position
            limit
            capacity    
        
        buffer.flip() -- mode transfer
        
        Read mode:
            position
            limit
            capacity    