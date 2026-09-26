# Important Notes - OOPs..

---
## Has-A relationship...

Imagine I tell you:

A car has an engine.

Would you write:

`class Car extends Engine
`
?

No.

Because:

```
Car IS-A Engine ❌
Car HAS-A Engine ✅
```

So you'd probably write something structurally like:
```
class Car {
Engine engine;
}
```
That is composition/has-a.