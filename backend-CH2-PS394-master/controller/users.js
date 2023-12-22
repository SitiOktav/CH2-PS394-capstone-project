import Users from "../models/usermodel.js"; 
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import dotenv from "dotenv";
dotenv.config();

export const getUsers = async(req, res) => {
    try{
        const users = await Users.findAll({
            attributes: ['id', 'username', 'email']
        });
        res.json(users);
    }catch(error){
        console.log(error);
    }
}

export const Register = async(req, res) => {
    const {username, email, password, confirmPassword} = req.body;
    if (password !== confirmPassword) return res.status(400).json({msg: "Password and Confirm Password do not match!"});
    if(!username) return res.status(200).json({msg: "Username must be filled!"});
    if(!email) return res.status(200).json({msg: "Email must be filled!"});
    if(!password && !confirmPassword) return res.status(200).json({msg: "Password must be filled!"});
    const existingUser = await Users.findOne({where:{email}});
    if(existingUser) return res.status(200).json({msg: "Email already exist!"});
    const existingName = await Users.findOne({where:{username}});
    if(existingName) return res.status(200).json({msg: "Username already exist!"});
    const salt = await bcrypt.genSalt();
    const hashPassword = await bcrypt.hash(password, salt);
    try{
        await Users.create({
            username: username,
            email: email,
            password: hashPassword
        });
        res.json({msg: "Register Success"});
    }catch(error){
        console.log(error);
    }
}


export const Login = async(req,res) => {
    try{
        const user = await Users.findAll({
            where:{
                email: req.body.email
            }
        });
        const match = await bcrypt.compare(req.body.password, user[0].password);
        if (!match) return res.status(400).json({msg: "Incorrect Password"});
        const userId = user[0].id;
        const username = user[0].username;
        const email = user[0].email;
        const accessToken = jwt.sign({userId, username, email}, process.env.ACCESS_TOKEN_SECRET, {
            expiresIn: '20s'
        });
        const refreshToken = jwt.sign({userId, username, email}, process.env.REFRESH_TOKEN_SECRET, {
            expiresIn: '1d'
        });
        await Users.update({refresh_token:refreshToken},{
            where:{
                id: userId
            }
        });
        res.cookie('refreshtoken', refreshToken,{
            httpOnly: true,
            maxAge: 24 * 60 * 60 * 1000

        });
        res.json({accessToken});

    }catch(error){
        res.status(404).json({msg: "Email Not Found"});
    }
}

export const Logout =async(req,res) => {
    const refreshToken = req.cookies.refreshtoken;
        if(!refreshToken) return res.sendStatus(204);
        const user = await Users.findAll({
            where:{
                refresh_token: refreshToken
            }
        });
        if(!user[0]) return res.sendStatus(204);
        const userId = user[0].id
        await Users.update({refresh_token:null},{
            where:{
                id: userId
            }
        });
        res.clearCookie('refreshtoken');
        return res.sendStatus(200);
}
