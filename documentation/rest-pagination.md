# Rest pagination

Let's consider a very common use case to get paginated information. In `BeersApplicationService` we have:

```java
public JhLiteApplicationPage<Beer> list(JhLiteApplicationPageable pagination) {
  // ...
}
```

To call that and expose a result using a rest service we can do something like that: 

```java
private final BeersApplicationService beers;

// ...

ResponseEntity<RestJhLiteApplicationPage<RestBeer>> list(@Validated RestJhLiteApplicationPageable pagination) {
  return ResponseEntity.ok(RestJhLiteApplicationPage.from(beers.list(pagination.toPageable()), RestBeer::from))
}
```

And we'll need a mapping method in `RestBeer`: 

```java
static RestBeer from(Beer beer) {
  // ...
}
```