# Url Shortener
### To build App container and run for the first time
* `cd ./url-shortener`
* `docker build --tag=url-shortener`
* `docker run --name=url-shortener -p 8080:8080 url-shortener:last`

### To stop container
* `docker stop url-shortener`

### To run again with logs (-i)
* `docker start -i url-shortener`
