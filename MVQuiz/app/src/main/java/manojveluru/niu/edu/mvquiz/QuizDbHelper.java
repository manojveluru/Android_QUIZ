package manojveluru.niu.edu.mvquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import manojveluru.niu.edu.mvquiz.QuizContract.*;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MVQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2+ " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT " +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }//end onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);

    }//end onUpgrade

    private void fillQuestionsTable()
    {
        Question q1 = new Question("Easy: Most lines in a C++ program end with a",
                ":", ";", "}", 2, Question.DIFFICULTY_EASY);
        addQuestion(q1);
        Question q2 = new Question("Medium: How many metres is one mile?",
                "1000", "1705", "1609", 3, Question.DIFFICULTY_MEDIUM);
        addQuestion(q2);
        Question q3 = new Question("Medium: An instance of a class is known as a/an",
                "Object", "Mirror", "Class", 1, Question.DIFFICULTY_MEDIUM);
        addQuestion(q3);
        Question q4 = new Question("Hard: Who is the first President of the United States of America?",
                "George Washington", "Abraham Lincoln", "Barack Obama", 1, Question.DIFFICULTY_HARD);
        addQuestion(q4);
        Question q5 = new Question("Hard: What are vertebrates that live both under water and on land called?",
                "Reptiles", "Amphibians", "Insects", 2, Question.DIFFICULTY_HARD);
        addQuestion(q5);
        Question q6 = new Question("Hard: What is the value of the expression 25 % 3?",
                "0", "2", "1", 3, Question.DIFFICULTY_HARD);
        addQuestion(q6);
        Question q7 = new Question("Hard: Which of the following increments x by 1?",
                "x+1", "x+=1", "1++", 2, Question.DIFFICULTY_HARD);
        addQuestion(q7);
        Question q8 = new Question("Hard: Vienna is the capital of?",
                "Sweden", "Italy", "Austria", 3, Question.DIFFICULTY_HARD);
        addQuestion(q8);
        Question q9 = new Question("Hard: Which operator is evaluated first in i = *(a * (b / (c - d)));",
                "/", "-", "*", 2, Question.DIFFICULTY_HARD);
        addQuestion(q9);
        Question q10 = new Question("Medium: What is the correct way to declare an integer variable named score?",
                "int score;", "integer score", "score int", 1, Question.DIFFICULTY_MEDIUM);
        addQuestion(q10);
        Question q11 = new Question("Medium: What is the value (in C++) of the expression 4 / 2 * 2?",
                "4", "1", "2", 1, Question.DIFFICULTY_MEDIUM);
        addQuestion(q11);
        Question q12 = new Question("Medium: What instruction will display data on the screen from a C++ program?",
                "display", "print", "cout", 3, Question.DIFFICULTY_MEDIUM);
        addQuestion(q12);
        Question q13 = new Question("Easy: Whats the result of 2*3?",
                "5", "6", "1", 2, Question.DIFFICULTY_EASY);
        addQuestion(q13);
        Question q14 = new Question("Easy: Every function must return a value.?",
                "Always", "Never", "Sometimes", 3, Question.DIFFICULTY_EASY);
        addQuestion(q14);


    }//end fillQuestionsTable

    private void addQuestion(Question question)
    {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }//end addQuestion

    public ArrayList<Question> getAllQuestions()
    {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            }while (c.moveToNext());
        }

        c.close();
        return questionList;
    }//end getAllQuestions

    public ArrayList<Question> getQuestions(String difficulty)
    {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);

        if (c.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            }while (c.moveToNext());
        }

        c.close();
        return questionList;
    }//end getQuestions
}
