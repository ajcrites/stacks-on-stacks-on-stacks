var MyStack;

// JavaScript arrays are nuts (like many scripting languages).  They implement
// *both* stacks and queues simultaneously.  If you want to use a JavaScript []
// like a stack, use only the `push` (add to the end of) and `pop` (remove
// from the end of) methods.  Subsequent push and pop operations work like
// a stack
//
// For a queue, it's more complicated.  `.push` adds (appends) to the end of
// the array, but, neither Stacks nor Queues do that.  `.pop` removes from
// the end of the array.
//
// This works great for stacks, but if you want to use a JavaScript array as a
// Queue, you have to use `.pop` (remove from the end) with `.unshift` (add
// to the beginning).
//
// Remember the [The Sound of Music Song](http://www.youtube.com/watch?v=1RW3nDRmu6k)
//
// Anyway, to use JavaScript arrays as Queues, use only `.pop` and `.unshift`.
//
// The confusing part is that if you wanted to use JS [] as a stack, you would
// use `.pop` and `.push`.  What happens to `.shift`??
(MyStack = function () {
    this.cur = [];
    this.prev = [];
}).prototype = {
    // Push elements onto the stack.  You can supply multiple elements, and the
    // argument order is based on LIFO (that is, the first element supplied
    // will be the last element out in the group).
    push: function () {
        this.cur.unshift.apply(this.cur, Array.prototype.slice.call(arguments).reverse());
    },

    pop: function () {
        var x, tmp;

        // The current queue has elements
        while (this.cur.length) {
            // Pseudo-pop by removing all of the elements from the end of the
            // current queue and storing the latest in `x`
            x = this.cur.pop();

            // If there are more elements, enqueue them to `prev`.  Otherwise,
            // `x` will have the last enqueued element (i.e. the one to be
            // popped).
            if (this.cur.length) {
                // `unshift` === `enqueue`
                this.prev.unshift(x);
            }
        }

        // Swap the queues; the last element will be returned, so the next
        // queue is one element shorter than the previous queue (minus the
        // `popped` element, or the last enqueued element
        tmp = this.cur;
        this.cur = this.prev;
        this.prev = tmp;

        return x;
    }
};

var x,
    s = new MyStack;
s.push("five", "four", "one")
console.log(s.pop());
s.push("two");
console.log(s.pop());
s.push("three");
while (x = s.pop()) {
    console.log(x);
}
