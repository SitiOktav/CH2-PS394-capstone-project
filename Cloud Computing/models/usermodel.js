import { Sequelize } from "sequelize";
import db from "../config/db.js";


    const Users = db.define('users', {
      username: {
        type: Sequelize.STRING
      },
      email: {
        type: Sequelize.STRING
      },
      password: {
        type: Sequelize.STRING
      },
      refresh_token: {
        type: Sequelize.TEXT
      },
    });

export default Users;
