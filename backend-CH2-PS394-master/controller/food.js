
import { Sequelize } from "sequelize";
import Kuliner from "../models/kulinermodel.js";

export const getAllfood = async(req, res) => {
    try{
        const food = await Kuliner.findAll({
            attributes: ['kuliner_id','nama_kuliner', 'jenis','asal_kota','deskripsi', 'image_url']
        });
        res.json(food);
    }catch(error){
        console.log(error);
    }
}

export const getFoodName = async (req, res) => {
    const name = req.params.name;

    try {
        const food = await Kuliner.findAll({
            where: {
                nama_kuliner: {
                    [Sequelize.Op.like]: `%${name}%`
                }
            }
        });
        res.json(food);
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: "Internal server error" });
    }
}
