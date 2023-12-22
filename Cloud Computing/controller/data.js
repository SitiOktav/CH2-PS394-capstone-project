import Destination from "../models/datamodel.js";
import { Sequelize } from "sequelize";

//Get all cities available
export const getAllCities = async(req, res) => {
    try{
        const cities = await Destination.findAll({
            attributes: ['City', 'City_image'],
            group: ['City', 'City_image']
        });
        res.json(cities);
    }catch(error){
        console.log(error);
    }
};

//find place by city
export const getCity = async (req, res) => {
    const city = req.params.city;

    try {
        const cityData = await Destination.findAll({
            where: {
                City: city
            },
            attributes: [
                'Place_Name',
                'Description',
                'City',
                'Category',
                'Address',
                'Location',
                'Price',
                'Rating',
                'Potensi_bencana',
                'Place_Image'
            ]
        });
        res.json(cityData);
    } catch (error) {
        console.log(error);
        res.status(404).json({ message: "City not found" });
    }
};

//get all categoty available
export const getAllCategories = async(req, res) => {
    try{
        const categories = await Destination.findAll({
            attributes: ['Category'],
            group: ['Category']
        });
        res.json(categories);
    }catch(error){
        console.log(error);
    }
}

//find place by category
export const getCategory = async (req, res) => {
    const category = req.params.category;
    try {
        const categoryData = await Destination.findAll({
            where: {
                Category: category
            },
            attributes: [
                'Place_Name',
                'Description',
                'City',
                'Category',
                'Address',
                'Location',
                'Price',
                'Rating',
                'Potensi_bencana',
                'Place_Image'
            ]
        });
        res.json(categoryData);
    } catch (error) {
        console.log(error);
        res.status(404).json({ message: "Category not found" });
    }
};


//get all place available
export const getAllPlace = async(req, res) => {
    try{
        const places = await Destination.findAll({
            attributes: [
                'Place_Name',
                'Description',
                'City',
                'Category',
                'Address',
                'Location',
                'Price',
                'Rating',
                'Potensi_bencana',
                'Place_Image',
                'Jumlah_Penduduk',
                'Jumlah_Crime',
                'Crime_per_Capita'
            ]
        });
        res.json(places);
    }catch(error){
        console.log(error);
    }
}
