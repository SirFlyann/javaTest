# Java Test

This project is the test I'm taking to apply for a job.

## Usage

This project has been dockerized to make it easier to test it. To run the application, simply run it from docker with

```sh
$ sudo docker run -p 8080:8080 sirflyann/javatest
```

The application will listen on port `8080`

The only URI the project has is `/sort`, with receives a `POST` request according to this example:
```
{
  "items": [
    {
      "id": "123",
      "ean": "7898100848355",
      "title": "Cruzador espacial Nikana -3000m -sem garantia",
      "brand": "nikana",
      "price": 820900.90,
      "stock": 1
    },	
    {
      "id": "u7042",
      "ean": "7898054800492",
      "title": "Espada de fótons Nikana Azul",
      "brand": "nikana",
      "price": 2199.90,
      "stock": 82
    },	
    {
      "id": "bb2r3s0",
      "ean": "2059251400402",
      "title": "Corredor POD 3000hp Nikana",
      "brand": "nikana",
      "price": 17832.90,
      "stock": 8
    },
    {
      "id": "321",
      "ean": "7898100848355",
      "title": "Cruzador espacial Nikana - 3000m - sem garantia",
      "brand": "trek",
      "price": 790300.90,
      "stock": 0
    },
    {
      "id": "80092",
      "ean": "",
      "title": "Espada de Fótons REDAV Azul",
      "brand": "trek",
      "price": 1799.90,
      "stock": 0
    },
    {
      "id": "7728uu",
      "ean": "7898100848355",
      "title": "Cruzador espacial Ekul - 3000m - sem garantia",
      "brand": "ekul",
      "price": 1300000.00,
      "stock": 1
    }
  ],
  "groupBy": "title;ean;brand",
  "orderBy": "title:asc;price:desc",
  "filter": "id=123"
}
```
The fields `groupBy`, `orderBy` and `filter` are optional, and if not received, 
the field `groupBy` will default to `ean;title;brand`, 
and the field `orderBy` will defualt to `stock:desc;price:asc`

## Dependencies

This project uses as main dependency the [Spring framework](https://spring.io/)

It also uses [lombok](https://projectlombok.org) to remove the need to type all of the getters and setters of the project
(there are not many getters and setters in this projects, but it could get a lot worse in bigger projects)

To group items by title, I used [fuzzywuzzy](https://github.com/xdrop/fuzzywuzzy) to filter titles that looked alike, with a precision of 70.

## Contributing

You don't. It's a test, I should do it alone.

## License

This project is under the [WTFPL License](http://www.wtfpl.net)
