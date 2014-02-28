/**
 * JavaScript implementation of `queue with two stacks` for
 * comparison.  Actually doesn't look much better, but the
 * enqueue multiple elements is cool IMO
 */
var MyQueue;

(MyQueue = function () {
    // Initialize incoming and outgoing queues
    this.in = [];
    this.out = [];
}).prototype = {
    enqueue: function () {
        // .concat merges two arrays together (appends all elements from
        // the source array to the target).  This is an idempotent action,
        // so it has to be written back to the original array (hence the `=`).
        //
        // The `.slice.call` is done to convert the special `arguments` object
        // to an array.  That is hyper-JavaScript specific.
        this.in = this.in.concat(Array.prototype.slice.call(arguments));
    },
    dequeue: function () {
        // Outgoing queue is not empty, so pop it.  JavaScript Arrays implement
        // stack operations.
        if (this.out.length) {
            return this.out.pop();
        }

        // In reality, this should be:
        // while (var x = this.in.pop()) { this.out.push(x)' }
        //
        // However, `.reverse` flips the incoming stack just as well.
        //
        // The incoming stack would be cleared by all the pop operations, so
        // we simply *clear* it by rewriting an emtpy array
        this.out = this.in.reverse();
        this.in = [];

        return this.out.pop();
    }
}

var x,
    q = new MyQueue;
q.enqueue("one", "two", "three");
console.log(q.dequeue());
q.enqueue("four");
console.log(q.dequeue());
q.enqueue("five");
while (x = q.dequeue()) {
    console.log(x);
}
