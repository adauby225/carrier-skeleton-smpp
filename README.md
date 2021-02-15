# carrier-skeleton-smpp
##Overview
carrier-skeleton-smpp is Library build on top of cloudhopper. 

* Real-world used library by Twitter across nearly every SMSC vendor and
   mobile operator around the world.  We've seen almost every variance in the
   SMPP protocol and this library handles it flawlessly.
 * Rigorous unit testing
 * Support for SMPP protocol:
    * Version 3.3
    * Version 3.4
    * Most of version 5.0
 * Uses non-blocking (NIO) sockets (via underlying Netty dependency, one thread
   can support 1 or more SMPP sessions)
 * Can support thousands of binds/connections using minimal resources and threads
 * Supports both client and server modes of the SMPP protocol (yes you can
   write your own SMPP server using this library as well as be a client to one)
 * Supports synchronous request mode (send request and block until response
   received)
 * Supports asynchronous request mode (send request, get a future response,
   and then decide when you'd like to wait/get a response)
 * Advanced support for SMPP "windowing":
    * Configurable window size per session
    * Waiting for a window slot to open up
    * Get a list of unacknowledged/in-flight PDUs if session disconnects
 * Configurable support for expiry of unacknowledged PDUs
 * Configurable counter metrics per client-session, server-session, or server.
 
	