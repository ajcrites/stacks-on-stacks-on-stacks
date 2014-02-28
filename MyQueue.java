// Hard to keep comments serious here (see MyStack), but this file will
// be a lot more rigid and serious

// Import Java's stack implementation.  I could implement `YourStack`,
// but this would require using some alternative to native arrays such as
// `ArrayList`.  Since those have to be imported anyway, I might as well import
// `Stack`.  Why the fuck should you write a Java program without importing
// anything?  Who programs without reading any documentation?
import java.util.Stack;

public class MyQueue<T> {
  // Inbox
  private Stack<T> in = new Stack<T>();

  // Outbox
  private Stack<T> out = new Stack<T>();

  // Add FIFO element
  public void enqueue(T elem) {
    in.push(elem);
  }

  // Queues are "FIFO..." first-in-first-out.  This means that the *first*
  // element to be enqueued will be the first returned on dequeue, even if it
  // were enqueued long ago.
  //
  // In order to do this, what we actually do is flip the entire `in` stack
  // by repeatedly popping it and pushing it to the `out` stack.
  //
  // Imagine that we've only enqueued so far ... On the *first* dequeue
  // it would look like this:
  //
  //     c         _            _         _           _          _            _         a
  //     b         _     ->     b         _     ->    _          b     ->     _         b
  //     a         _            a         c           a          c            _         c
  // IN STACK  OUT STACK    IN STACK  OUT STACK    IN STACK  OUT STACK    IN STACK  OUT STACK
  //
  // The `in` stack has been flipped on its head
  //
  // ***Remember*** that *stacks* go `LIFO`, that is *last-in-first-out*, so
  // since we see that `a` as at the *top* of the `out` stack, it will be
  // the first to be popped
  //
  // As far as our Queue knows, that means that `a` is the first element to
  // be dequeued.  In fact, when dequeue is called, `a` is popped immediately,
  // so we see this:
  //
  //     _
  //     _         b
  //     _         c
  // IN STACK   OUT STACK
  //
  // If we try to dequeue again (in fact, *any* time we try dequeue while
  // `out` is not empty) we simply pop the `out` stack.  Once `out` is empty
  // again, go back to line 30.
  public T dequeue() {

    // As (hopefully) made clear above, continue to pop from the outgoing
    // stack which is the flipped incoming stack and thus has switched
    // the LIFO `Stack` to the FIFO `Queue` by dropping it on its head
    //
    // We only need to pop once per `dequeue` since `dequeue` only returns
    // one element
    if (!out.empty()) {
      // The operation ends here.  If `out` was not empty, *stop reading
      // all the other comments*!
      return out.pop();
    }

    // The outgoing stack is empty, so there is work to be done.  We have
    // to flip the incoming stack on its head *into* the outgoing stack.
    if (!in.empty()) {
      // The complete flip, top-to-bottom
      while (!in.empty()) {
        out.push(in.pop());
      }
      // Our goal is to return the very first element pushed/enqueued
      // via `dequeue`.  This will wind up at the top of the `out` stack
      // because it is at the bottom of the `in` stack, which has been flipped
      return out.pop();
    }

    // Both stacks are empty
    return null;
  }

  // This is just done for some sugar in the sample ... Make it easy to
  // dequeue in a loop
  public boolean empty() {
    return in.empty() && out.empty();
  }

  public static void main(String[] args) {
    MyQueue<String> q = new MyQueue<String>();
    q.enqueue("one");
    q.enqueue("two");
    q.enqueue("three");
    System.out.println(q.dequeue());
    q.enqueue("four");
    System.out.println(q.dequeue());
    q.enqueue("five");
    while (!q.empty()) {
      System.out.println(q.dequeue());
    }
  }
}
