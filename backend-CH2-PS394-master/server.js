import express from "express";
import dotenv from"dotenv";
import cookieParser from "cookie-parser";
import cors from "cors";
import db from "./config/db.js";
import router from "./routes/index.js";
dotenv.config();

const app = express();

try{
    await db.authenticate();
    console.log('Database Connection Success!');
}catch(error){
    console.error(error);
}

app.use(cors({credentials:true, origin: 'http://localhost:8000'}))
app.use(cookieParser());
app.use(express.json());
app.use(router);

app.listen(8000, ()=> console.log('Server running at port 8000'));

