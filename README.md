# carrier-skeleton-smpp
##Overview
carrier-skeleton-smpp is Library build on top of cloudhopper. 

 * Support for SMPP protocol:
    * Version 3.3
    * Version 3.4
    * Most of version 5.0
 
 * Supports both client and server modes of the SMPP protocol 
 * Manage SMPP binds
 * Manage ESME sessions
 * Provide abstract handlers for SMPP requests
 * Provide abstract handlers for SMPP responses 
 
 This library is made to facilitate development of a SMPP gateway.
 
## Installation

```
repositories {
	maven { url 'https://jitpack.io'}	
}

dependencies {
	implementation 'com.github.adauby225:carrier-skeleton-smpp:1.0.2'
}
```


## Demo Code / Tutorials

There are some examples of how to use this library:

```
src/test/java/com/carrier/smpp/demo
```
