# NetworkTools

[![](https://jitpack.io/v/alexsgi/network-tools.svg)](https://jitpack.io/#alexsgi/network-tools)

## 1. Import

**Gradle:**
```gradle
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```
```gradle
dependencies {
	implementation 'com.github.alexsgi:network-tools:VERSION'
}
```
**Maven:**
```maven
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```
```maven
<dependencies>
	<dependency>
	    <groupId>com.github.alexsgi</groupId>
	    <artifactId>network-tools</artifactId>
	    <version>VERSION</version>
	</dependency>
</dependencies>
```

## 2. Usage

###  Network operations :
 
 **Ping :**
 ```java
NetworkTools.ping(String url, CommandCallback callback);
```
```java
NetworkTools.ping("www.example.com", new CommandCallback() {  
	@Override  
	public void onFinish(String output) {  
		System.out.println(output);  
	}  
  
	@Override  
	public void onError(Exception e) {  
		e.printStackTrace();
	}  
});
```
---

**Tracerout (*tracert* , *traceroute*) :**
```java
NetworkTools.tracerout(String url, CommandCallback callback);
```
```java
NetworkTools.tracerout("www.example.com", new CommandCallback() {  
	@Override  
	public void onFinish(String output) {  
		System.out.println(ouput);
	}  
  
	@Override  
    public void onError(Exception e) {  
	    e.printStackTrace();
	}  
});
```
**Important :** 
```ping```  and  ```tracerout``` always check if the OS is Windows or Linux. On this way it runs another command on the command line (Linux : ```tracerout``` ; Windows : ```tracert```). ```String output``` is not formatted - just the output of the OS.

→ **still in development**

---

**Check for devices in network :** 
```java
NetworkTools.checkForHosts(String subnet, HostCallback callback);
NetworkTools.checkForHosts(String subnet, HostCallback callback, int timeout);
NetworkTools.checkForHosts(String subnet, HostCallback callback, int timeout, int beginIndex);
NetworkTools.checkForHosts(String subnet, HostCallback callback, int timeout, int beginIndex, int endIndex);
```
```java
NetworkTools.checkForHosts("192.168.178", new HostCallback() {  
    @Override  
    public void onDeviceFound(String host) {  
        System.out.println(host + " is reachable");
    }  
  
    @Override  
    public void onFinish(String[] hosts) {  
	    System.out.println(hosts.length + " devices found in network");
    }  
});
```
Notice :
*default timeout : 1000 ms </br>
default begin index : 0 </br>
default end index : 254*

---

### TOR exit node verification :

All TOR exit node addresses are online available. Let's check if an IP address is a TOR exit address :
```java
TorData.checkIpIsExitNode("XX.XXX.XXX.XXX", new CommandCallback() {  
    @Override  
    public void onFinish(String output) {  
          if(output == null) {
	          System.out.println("IP is not a TOR exit node");
	      } else {
			  System.err.println("TOR exit node found !");
		  }
    }  
  
    @Override  
    public void onError(Exception e) { 
	      e.printStackTrace();
    }  
});
```
----

Following options are available as well :
```java
static ArrayList<ExitNode> getAllExitNodes();
```
```java
static void setTorExitAddressURL(String newUrl);
```
```java
static void resetTorExitAddressURL();
```

---

### Locale lookup :

Get the country code by the country name :
```java
LocalFinder.getCountryCode(String countryName);
```
Get the country name by the country code :
```java
LocalFinder.getCountryName(String countryCode);
```
```java
String name = LocalFinder.getCountryName("DE");
```
