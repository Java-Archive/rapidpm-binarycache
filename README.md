# BinaryCache - a microservice-based cache

## Overview
 - RapidPM Microservice
 - Different JCache implementations, e.g. EHCache or Hazelcast Cache
 - Inmemory interface for direct access to the cache functionality 
 - REST endpoints for access via http

## Build your own cache
This project comes with an so called **aggregate** module, enabling the configuration of the cache components 
by using Maven profiles. By default, the aggregate will produce a jar containing the ehcache and a corresponding REST endpoint to the cache. 
Here's an example how to produce a cache with REST and Hazelcast:
1. Disable the default profile **binarycache-provider-ehcache**
2. Enable the profile **binarycache-proivder-hazelcast**
3. Check that the **binarycache-connect-rest** profile is still active
4. Execute a clean&install for the **aggregate** module  
5. You'll find a customized jar cache-hazelcast-rest-xxx.jar in the modules target folder

## Interfaces
Currently there are two different ways to access the cache from a clients perspective.
First, you can use the provided **inmemory** interface. Just add the following dependency to your Maven project.
You'll find an example how to use it in the module **rapidpm-binarycache-modules-client-connect-inmemory** 

```xml
  <dependency>
    <groupId>org.rapidpm.binarycache</groupId>
     <artifactId>rapidpm-binarycache-modules-app-cache-api</artifactId>
     <version>...</version>
   </dependency>
```

Second, you could also use the **REST enpoints**, provided by the BinaryCache implementation. 
Using the BinaryCacheRestClient, your calls will be transformed into http request to the cache.
Add the following dependency to use it in your project.

```xml
  <dependency>
    <groupId>org.rapidpm.binarycache</groupId>
     <artifactId>rapidpm-binarycache-modules-client-connect-rest</artifactId>
     <version>...</version>
   </dependency>
```
 
## Provider
### Ehcache
From the website [ehcache.org](http://www.ehcache.org/):  
*"Ehcache is an open source, standards-based cache that boosts performance, offloads your database, and simplifies scalability. It's the most widely-used Java-based cache because it's robust, proven, full-featured, and integrates with other popular libraries and frameworks. Ehcache scales from in-process caching, all the way to mixed in-process/out-of-process deployments with terabyte-sized caches."*

### Hazelcast
The implementation of the JSR 107 (JCache) from Hazelcast offers a distribution of the cache content other several instances, based on Hazelcast's own in-mmeory data grid solution. See [Hazelcast Cache](https://hazelcast.com/use-cases/caching/jcache-provider/) for more information.