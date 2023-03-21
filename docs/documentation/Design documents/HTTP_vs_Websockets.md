# **HTTP vs Websockets**
HTTP and Websockets are 2 different ways to handle data traffic between the server and the client, it's important we look into when we should which technology. It is a requirement both should be used. In this document we look into the pro's and con's for each approach and decide when we are going to use Websockets or http in our application

### HTTP
##### Pro's:
-   HTTP is a stateless protocol, which makes it very easy to implement.
-   It uses a request-response model, which makes it easier to manage and debug.
-   It allows caching and compression of content, which can improve performance.
##### Con's:
-   HTTP is not made for bi-directional data transfer, as it is a request-response protocol requires the client to constantly send a request to the server to check for updates. This can be overcome with long polling, but that has an increased server load compared to websockets.
-   HTTP, even with long polling, has an higher latency and slower response times compared to Websockets.
##### Use cases:
Basic HTTP is best used for static or data that doesn't need to update frequently. Configuration settings for example are ideal for basic HTTP. Using long polling more bi-directional-ish communication can be achieved when needed.

#### Websockets
##### Pro's:
-   Websockets are bi-directional, which means both the server and client can initiate data transfer.
-   Websockets have low latency and high throughput, making them better for real-time data display that requires constant updates
##### Con's:
-	Websockets are harder to implement then basic HTTP calls
-   Websockets require more resources and a persistent connection, which can put strain on the server
##### Use cases:
Websockets are ideal for real time applications were data between the server and client needs to be exchanged frequently with low latency. User facing data synchronization updates between the client and server are a good use case for Websockets, since you generally want to have the lowest amount of latency possible when propagating changes between users.


## When should we use which approach?
#### HTTP
Request response based operations, anything that shouldnt update that much
- Establishing server connection
- Joining/leaving Boards
- Customization settings
- Loading in initial data

Operation suitable for (long) polling
- General Board CRUD
- General Lists CRUD
- Tag management

#### Websockets

Anything related to cards and in the card edit menu
- General Card CRUD
- Moving Cards between lists especially
- General Task CRUD
- General Tag CRUD
  
