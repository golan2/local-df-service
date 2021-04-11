kill -9 $(ps aux | grep LocalServerApp | grep java | awk {'print$2'})

