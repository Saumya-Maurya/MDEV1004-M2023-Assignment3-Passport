/*
 * Filename: src/models/model.js
 * Sanket Parab - 2200555449 
 * Saumya Maurya - 200553573
 * Ruchit Suhagia – 200554055
 * Tanveer Singh - 200554065
 * Date: 2023-12-12
 */

const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const passportLocalMongoose = require('passport-local-mongoose');

// Defining the schema for the "Books" model
const BooksSchema = new Schema({
    BooksName: {
        type: String,
        required: 'Enter the book name'
    },
    ISBN: {
        type: String,
        required: 'Enter the book ISBN'
    },
    Rating: {
        type: Number,
        required: 'Enter the book rating'
    },
    Author: {
        type: String,
        required: 'Enter the book author'
    },
    Genre: {
        type: String,
        required: 'Enter the book genre'
    },
    created_date: {
        type: Date,
        default: Date.now
    }
});

const Books = mongoose.model('Books', BooksSchema);

const UserSchema = new Schema({
    username: String,
    password: String,
});

UserSchema.plugin(passportLocalMongoose);

const User = mongoose.model('User', UserSchema);

module.exports = {
    BooksSchema,
    Books,
    User
};

