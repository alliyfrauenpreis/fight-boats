/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// ALL BELOW CODE IS TEST CODE PULLED FROM AN ONLINE SOURCE, NOT WRITTEN BY ME.


//package shipsinthenight;
//
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// */
//public class DiscoveryThread implements Runnable {
//
//    DatagramSocket socket;
//    
//    @Override
//    public void run() {
//
//        System.out.println("Starting server thread.....");
//        
//        try {
//            try {
//                // open a listening socket
//                socket  = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
//                socket.setBroadcast(true);
//                System.out.println("Data socket open on port 8888");
//                
//            } catch (UnknownHostException ex) {
//                
//                System.out.println("Data socket fail");
//                Logger.getLogger(DiscoveryThread.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } catch (SocketException ex) {
//            
//                System.out.println("Data socket fail");
//            Logger.getLogger(DiscoveryThread.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    
//    
//    }
//    
//    public DiscoveryThread getInstance(){
//        
//        return DiscoveryThreadHolder.instance;
//    }
//    
//    private static class DiscoveryThreadHolder{
//        
//        private static final DiscoveryThread instance = new DiscoveryThread();
//    }
//    
//}
