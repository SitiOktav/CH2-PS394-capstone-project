import express from "express";
import {getUsers, Register, Login, Logout} from "../controller/users.js"
import { verifyToken } from "../middleware/verifytoken.js";
import { refreshToken } from "../controller/refreshtoken.js";
import { getAllCities, getAllCategories, getCity, getCategory, getAllPlace } from "../controller/data.js";
import {getAllfood, getFoodName,} from "../controller/food.js"

const router = express.Router();

router.get('/users',verifyToken,getUsers);
router.post('/register',Register);
router.post('/login',Login);
router.get('/token',refreshToken);
router.delete('/logout',Logout);

router.get('/cities', getAllCities)
router.get('/categories', getAllCategories)
router.get('/cities/:city', getCity)
router.get('/categories/:category', getCategory)
router.get('/place', getAllPlace)

router.get('/food/', getAllfood)
router.get('/food/:name', getFoodName)
export default router;
