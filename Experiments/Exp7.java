/*
Same Worker vs Two Workers

This one is specifically for your doubt.

Create two versions.

Version A
Worker #1
   ↑     ↑
Thread A Thread B

Both threads use the same worker object.

Version B
Worker #1        Worker #2
   ↑                ↑
Thread A          Thread B

Now ask:

If Worker.doSomething() is a synchronized instance method, are both threads competing for the same monitor?

And then:

If the worker contains the actual shared resource, does the answer change?

Do not assume "same task" means "same resource."

That's exactly the mental model we need to build.
 */