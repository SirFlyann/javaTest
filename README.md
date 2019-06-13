# Java Test

This project is the test I'm taking to apply for a job.

## Usage

This project has been dockerized to make it easier to test it. To run the application, simply run it from docker with

```sh
$ sudo docker run -p 8080:8080 sirflyann/javatest
```

The application will listen on port `8080`

## Dependencies

This project uses as main dependency the [Spring framework](https://spring.io/)

It also uses [lombok](https://projectlombok.org) to remove the need to type all of the getters and setters of the project
(there are not many getters and setters in this projects, but it could get a lot worse in bigger projects)

To group items by title, I used [fuzzywuzzy](https://github.com/xdrop/fuzzywuzzy) to filter titles that looked alike, with a precision of 70.

## Contributing

You don't. It's a test, I should do it alone.

## License

This project is under the [WTFPL License](http://www.wtfpl.net)
