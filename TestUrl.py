import datetime
import urllib.request

indice = 0
while 1:

    try:
        contents = urllib.request.urlopen("http://localhost:8080/stock/stock/list").read()
    except:
        now = datetime.datetime.now()
        print (now.strftime("%Y-%m-%d %H:%M:%S"))


