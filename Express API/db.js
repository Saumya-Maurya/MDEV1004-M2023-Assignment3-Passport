/*
 * Filename: index.js
 * Sanket Parab - 2200555449 
 * Saumya Maurya - 200553573
 * Ruchit Suhagia – 200554055
 * Tanveer Singh - 200554065
 * Date: 2023-12-12
 */


const mongoose = require("mongoose");
const { Books } = require('./src/models/model');
const { User } = require('./src/models/model');

// MongoDB connection URI
const MONGOURI = "mongodb+srv://group9:12345@cluster0.f57jmjb.mongodb.net/fav_books?retryWrites=true&w=majority";

// Async function to initiate MongoDB connection
const InitiateMongoServer = async () => {
    try {
        // Connecting to MongoDB
        await mongoose.connect(MONGOURI, {
            useNewUrlParser: true,
            useUnifiedTopology: true
        });

        console.log("Connected to Database!");

        // Sample books data
        const favBooks = [
            {
                BooksName: "To Kill a Mockingbird",
                ISBN: "978-0061120084",
                Rating: 4.5,
                Author: "Harper Lee",
                Genre: "Fiction",
            },
            {
                BooksName: "1984",
                ISBN: "978-0451524935",
                Rating: 4.3,
                Author: "George Orwell",
                Genre: "Science Fiction",
            },
            {
                BooksName: "The Great Gatsby",
                ISBN: "978-0743273565",
                Rating: 4.2,
                Author: "F. Scott Fitzgerald",
                Genre: "Classics",
            },
            {
                BooksName: "Pride and Prejudice",
                ISBN: "978-0141439518",
                Rating: 4.6,
                Author: "Jane Austen",
                Genre: "Romance",
            },
            {
                BooksName: "The Hobbit",
                ISBN: "978-0345339683",
                Rating: 4.7,
                Author: "J.R.R. Tolkien",
                Genre: "Fantasy",
            },
            {
                BooksName: "The Catcher in the Rye",
                ISBN: "978-0316769174",
                Rating: 4.0,
                Author: "J.D. Salinger",
                Genre: "Coming of Age",
            },
            {
                BooksName: "The Lord of the Rings",
                ISBN: "978-0544003415",
                Rating: 4.9,
                Author: "J.R.R. Tolkien",
                Genre: "Fantasy",
            },
            {
                BooksName: "Harry Potter and the Sorcerer's Stone",
                ISBN: "978-0590353427",
                Rating: 4.8,
                Author: "J.K. Rowling",
                Genre: "Fantasy",
            },
            {
                BooksName: "The Da Vinci Code",
                ISBN: "978-0307474278",
                Rating: 3.8,
                Author: "Dan Brown",
                Genre: "Mystery",
            },
            {
                BooksName: "The Hunger Games",
                ISBN: "978-0439023528",
                Rating: 4.2,
                Author: "Suzanne Collins",
                Genre: "Dystopian",
            },
            {
                BooksName: "The Shining",
                ISBN: "978-0307743657",
                Rating: 4.3,
                Author: "Stephen King",
                Genre: "Horror",
            },
            {
                BooksName: "Gone with the Wind",
                ISBN: "978-1451635621",
                Rating: 4.5,
                Author: "Margaret Mitchell",
                Genre: "Historical Fiction",
            },
            {
                BooksName: "The Alchemist",
                ISBN: "978-0061122415",
                Rating: 4.2,
                Author: "Paulo Coelho",
                Genre: "Philosophical Fiction",
            },
            {
                BooksName: "The Road",
                ISBN: "978-0307265432",
                Rating: 4.1,
                Author: "Cormac McCarthy",
                Genre: "Post-Apocalyptic",
            },
            {
                BooksName: "Brave New World",
                ISBN: "978-0060850524",
                Rating: 4.0,
                Author: "Aldous Huxley",
                Genre: "Dystopian",
            },
            {
                BooksName: "The Girl with the Dragon Tattoo",
                ISBN: "978-0307269751",
                Rating: 4.4,
                Author: "Stieg Larsson",
                Genre: "Mystery",
            },
            {
                BooksName: "A Song of Ice and Fire",
                ISBN: "978-0553103540",
                Rating: 4.7,
                Author: "George R.R. Martin",
                Genre: "Fantasy",
            },
            {
                BooksName: "The Fault in Our Stars",
                ISBN: "978-0525478812",
                Rating: 4.5,
                Author: "John Green",
                Genre: "Young Adult",
            },
            {
                BooksName: "The Chronicles of Narnia",
                ISBN: "978-0066238500",
                Rating: 4.6,
                Author: "C.S. Lewis",
                Genre: "Fantasy",
            },
            {
                BooksName: "The Name of the Wind",
                ISBN: "978-0756404741",
                Rating: 4.8,
                Author: "Patrick Rothfuss",
                Genre: "Fantasy",
            },
            {
                BooksName: "Fahrenheit 451",
                ISBN: "978-1451673315",
                Rating: 4.2,
                Author: "Ray Bradbury",
                Genre: "Dystopian",
            },
        ];

        // Check if example data already exists in the Books collection
        const existingBooks = await Books.find({});
        if (existingBooks.length > 0) {
            console.log('Example data already exists. Skipping insertion.');
        } else {
            // Insert sample data into the Books collection
            await Books.insertMany(favBooks);
            console.log('Data Inserted Successfully');
        }
    } catch (e) {
        console.log(e);
        throw e;
    }
};

module.exports = InitiateMongoServer;
