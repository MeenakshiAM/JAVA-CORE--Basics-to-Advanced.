# Important Notes 

Look at this:

interface Printable {
void print();
}

For an interface method, Java treats that as:

public abstract void print();

So your interface is effectively saying:

interface Printable {
public abstract void print();
}

You just don't have to write public abstract because those modifiers are implicit for ordinary interface methods.
```
public
    ↓
protected
    ↓
package-private (default)
    ↓
private
```