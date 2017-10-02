//Non-blocking IO
//1 By using Java’s NIO API directly or through a library like Netty.
//  Such libraries, too, can serve many connections with a reasonably-sized 
//  dedicated thread pool.
//2 Directly working with the Promise type makes a lot of sense.




//Blocking IO
//Sometimes, there is no NIO-based library available. 
// For instance, most database drivers you’ll find in the Java world 
// nowadays are using blocking IO. If you made a query to your database
// with such a driver in order to respond to a HTTP request, 
// that call would be made on a web server thread.

//To avoid that, place all the code talking to the database inside 
// a future block, like so:
// get back a Future[ResultSet] or something similar:
//Future {
//  queryDB(query)
//}

// It’s probably a good idea to create a dedicated ExecutionContext 
// that you will have in scope in your database layer.
//You can create an ExecutionContext from a Java ExecutorService, 
//which means you will be able to tune the thread pool for executing 
//your database calls asynchronously independently from the rest of 
//your application:

import java.util.concurrent.Executors
import concurrent.ExecutionContext
val executorService = Executors.newFixedThreadPool(4)
val executionContext = ExecutionContext.fromExecutorService(executorService)


//Long-running computations
//Future {
//  longRunningComputation(data, moreData)
//}