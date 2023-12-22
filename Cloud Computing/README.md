Make a .env file and fill it with 

```
ACCESS_TOKEN_SECRET=<ACCESS_TOKEN_SECRET> //Random 32 characters
REFRESH_TOKEN_SECRET=<REFRESH_TOKEN_SECRET> //Random 32 characters


DB_USERNAME=<user username> //username in cloud sql
DB_PASSWORD=<user password> //username in cloud sql
DB_NAME=<db name> //username in cloud sql
DB_HOST=<host ip> v//username in cloud sql
```
also change the ```port``` and ```socket``` in [db.js](config\db.js) according what's in your Cloud SQL. We used MySQL for this API. 

To run API, run 
```
npm install
```
in the command line, then run 
```
npm run start
```

You could find the API Documentation for this project here:
[API DOCUMENTATION](https://docs.google.com/document/d/1_TaMyry-y0MjxuYZYlbdVLyJ0Jc4ImUNfkv721VHIZY/edit?usp=sharing)
