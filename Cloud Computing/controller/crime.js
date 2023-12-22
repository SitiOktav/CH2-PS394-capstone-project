import { Sequelize } from "sequelize";
import Destination from "../models/datamodel.js";

export const getCrimelocation = async (req, res) => {
    const location = req.params.name;

    try {
        const crime = await Destination.findAll({
            where: {
                Location: {
                    [Sequelize.Op.like]: `%${location}%`
                }
            },
            attributes: [
                'Location',
                'Jumlah_Penduduk',
                'Jumlah_Crime',
                'Crime_per_Capita',
                'Criminal_Rate'
            ]
                
        });
        res.json(crime);
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: "Internal server error" });
    }
}

export const getCrimeplace = async (req, res) => {
    const place = req.params.name;

    try {
        const crime = await Destination.findAll({
            where: {
                Place_name: {
                    [Sequelize.Op.like]: `%${place}%`
                }
            },
            attributes: [
                'Location',
                'Jumlah_Penduduk',
                'Jumlah_Crime',
                'Crime_per_Capita',
                'Criminal_Rate'
            ]
                
        });
        res.json(crime);
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: "Internal server error" });
    }
}
