/*
 * Filename: src/controllers/controller.js
 * Sanket Parab - 2200555449 
 * Saumya Maurya - 200553573
 * Ruchit Suhagia – 200554055
 * Tanveer Singh - 200554065
 * Date: 2023-12-12
 */


const mongoose = require("mongoose");
const { BooksSchema } = require("../models/model");
const passport = require('passport');
const { User } = require('../models/model');


const Book = mongoose.model('books', BooksSchema);

// Controller to register a new user
exports.registerUser = (req, res) => {
    User.register(new User({ username: req.body.username }), req.body.password, (err) => {
        if (err) {
            console.log(err);
            return res.status(500).json({ error: err.message });
        }
        res.json({ message: 'User registered successfully' });
    });
};

// Controller to log in a user
exports.loginUser = (req, res, next) => {
    passport.authenticate('local', (err, user, info) => {
        if (err) {
            return res.status(500).json({ error: err.message });
        }
        if (!user) {
            return res.status(401).json({ message: 'Authentication failed' });
        }
        req.logIn(user, (err) => {
            if (err) {
                return res.status(500).json({ error: err.message });
            }
            res.json(req.user);
        });
    })(req, res, next);
};

// Controller to add a new book
exports.addNewBook = (req, res) => {
    const newBook = new Book(req.body);

    newBook.save()
        .then(() => {
            res.json(newBook);
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({ error: err.message });
        });
}

// Controller to get all books
exports.getBooks = (req, res) => {
    Book.find({})
        .then(data => {
            res.send(data);
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({ error: err.message });
        });
}

// Controller to get a book by ID
exports.getBookWithID = (req, res) => {
    Book.findById(req.params.bookId)
        .then(data => {
            if (!data) {
                res.status(404).json({ message: 'Book not found' });
            } else {
                res.send(data);
            }
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({ error: err.message });
        });
}

// Controller to update a book by ID
exports.updateBook = (req, res) => {
    Book.findOneAndUpdate({ _id: req.params.bookId }, req.body, { new: true })
        .then(book => {
            if (!book) {
                return res.status(404).json({ message: 'Book not found' });
            }
            res.json(book);
        })
        .catch(err => {
            res.status(500).json({ error: err.message });
        });
}

// Controller to delete a book by ID
exports.deleteBook = (req, res) => {
    Book.deleteOne({ _id: req.params.bookId })
        .then(() => {
            res.json({ message: 'Successfully deleted book' });
        })
        .catch((err) => {
            res.status(500).json({ error: err.message });
        });
}
