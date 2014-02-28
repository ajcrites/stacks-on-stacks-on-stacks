// Import Java standard Queue.  This is an Interface rather than a concrete class
import java.util.Queue;

// Because Java is so awesome, we have to have a concrete class to implement
// the Queue interface.  Java Vanguards will no doubt go on to talk about how
// excellent it is that we are using a concrete class to implement the Queue
// interface ... my lowly scripting mind things that a concrete Queue would
// be lovely, but the Java Gods know better
import java.util.LinkedList;

public class MyStack<T> {
  // Push target
  private Queue<T> cur = new LinkedList<T>();

  // Swap on pop (not quite like *Hop on Pop*)
  private Queue<T> prev = new LinkedList<T>();

  // Implementation of Stack.push; add element to the top of the stack
  //
  // It secretly doesn't, but the real magic of programming is
  // ***information hiding***
  public void push(T elem) {
    cur.add(elem);
  }

  // This is where the magic happens.  Dequeue the entire "current" queue
  // and enqueue every element except for the last, which is to be
  // returned (Stack is *L*ast in *F*irst out)
  //
  // Then swap queues so this can be done by enqueuing elements to the
  // other queue
  public T pop() {
    // Initialize the value to return `x` as null.
    // Because Java is awful, it doesn't realize that I will check the
    // condition that cannot be met by `cur` being empty and return `null`.
    // In any sane programming language, `T x;` would be enough, but
    // ***because `Java`***, I have to initialize to `null` because I might
    // have been too dumb to not return null by not checking all possible
    // conditions
    T x = null;

    // Necessary for the swap.  I can't blame Java for this
    Queue<T> tmp;

    // java.util.Stack.empty is a method.  java.util.Queue.empty is not.
    // Then again, java.util.Stack is a concrete class.  I can't say I'm not
    // shocked that Java is inconsistent here. REHL uses `httpd` for apache
    // while Debian uses `apache2`.  Consistency is developer anathema.
    if (cur.isEmpty()) {
      return null;
    }

    while (!cur.isEmpty()) {
      // Ah, yes.  `remove`.  I've always heard of that when dealing with
      // queues.  Enqueue and Dequeue must be for stupid British people.
      //
      // At any rate, we store the each element **dequeued** and eventually
      // we have the last element which is to be *popped*.
      x = cur.remove();

      // If the current queue is not empty, we need to enqueue what has been
      // dequeued to the alternate queue ... **excuse me**.. we need to *add*
      // what has been *removed*.
      if (!cur.isEmpty()) {
        // Using the lovely `add`.  Java has really gone above and beyond to
        // destroy convention by replacing the gnarly `enqueue` terminology
        // with the much simpler Americanized `add`
        prev.add(x);
      }
    }

    // Perform the swap.  This allows us to transparently work with `cur`
    // as if it were the same object!  Note the lack of `this`.  Java is so
    // smart that it makes us type out literally everything; it will complain
    // if we don't allocate objects with the correct generic declaration, we
    // have to initialize elements even if alternate conditions ensure that
    // they will not be returned, and  if we don't return the right thing
    // or *forget to declare that a method might throw an exception,*
    // we get a compile time error ...  but apparently typing `this.` is just
    // ***too much to ask***
    tmp = cur;
    cur = prev;
    prev = tmp;

    // Return the pseudo-popped element
    return x;
  }

  // Not really necessary, but this trivializes continuous popping in
  // my example
  public boolean empty() {
    return cur.isEmpty() && prev.isEmpty();
  }

  // Example code
  public static void main(String[] args) {
    MyStack<String> s = new MyStack<String>();

    s.push("five");
    s.push("four");
    s.push("one");
    System.out.println(s.pop());
    s.push("two");
    System.out.println(s.pop());
    s.push("three");
    while (!s.empty()) {
      System.out.println(s.pop());
    }
  }
}
