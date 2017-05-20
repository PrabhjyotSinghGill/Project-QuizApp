package com.project.course.project;


import com.google.firebase.database.DatabaseReference;

public class Questions {

    static int numQuestions = 12;
    static String[] questions = new String[numQuestions];
    static String[][] options = new String[numQuestions][4];
    static int[] answers = new int[numQuestions];
    private DatabaseReference mQuestionsReference;

    public void load(DatabaseReference dbref){
        mQuestionsReference = dbref;

        questions[0] = "An arm of which ocean lies to the east of the island which was for a time called Formosa?";
        options[0][0] = "Atlantic";
        options[0][1] = "Indian";
        options[0][2] = "Pacific";
        options[0][3] = "Southern";
        answers[0] = 3;


        questions[1] = "According to an International Union for Conservation of Nature (IUCN) study published in 2017, what is the source of up to 30% of the 'plastic soup' polluting the world’s oceans?";
        options[1][0] = "Tiny plastic bits washed off products such as synthetic clothes and car tyres";
        options[1][1] = "Collars from packs of drink cans";
        options[1][2] = "One-time-use plastic bags";
        options[1][3] = "Fishing nets";
        answers[1] = 1;



        questions[2] = "When and where did the Acol system evolve?";
        options[2][0] = "1930s in London, UK";
        options[2][1] = "1940s and 1950s in Philadelphia, USA";
        options[2][2] = "1930s in Indianapolis, USA";
        options[2][3] = "1960s in New York, USA";
        answers[2] = 1;



        questions[3] = "What nationality is the director of the BAFTA- and Palme d'Or-winning drama 'Paris, Texas' (1984)?";
        options[3][0] = "American";
        options[3][1] = "German";
        options[3][2] = "British";
        options[3][3] = "French";
        answers[3] = 2;



        questions[4] = "Tom Jones sang the theme song to which James Bond film, released in 1965?";
        options[4][0] = "You Only Live Twice";
        options[4][1] = "Live and Let Die";
        options[4][2] = "Thunderball";
        options[4][3] = "Casino Royale";
        answers[4] = 3;



        questions[5] = "What does the Latin term 'compos mentis' mean?";
        options[5][0] = "Of sound mind";
        options[5][1] = "Camp men";
        options[5][2] = "Fertiliser repairs";
        options[5][3] = "Communist Postmen";
        answers[5] = 1;



        questions[6] = "From 1949 to 1989, with final closure in 1991, what did the Union of Soviet Socialist Republics (USSR) do in the then Kazakh Republic?";
        options[6][0] = "Carry out its main nuclear testing and explosions";
        options[6][1] = "Drill the main infantry units in its army";
        options[6][2] = "Carry out tank manoeuvre drills";
        options[6][3] = "Establish its main biological research centre";
        answers[6] = 1;

        questions[7] = "What year did Albert Einstein die?";
        options[7][0] = "1954";
        options[7][1] = "1949";
        options[7][2] = "1960";
        options[7][3] = "1955";
        answers[7] = 4;

        questions[8] = "Which is the largest planet in the solar system?";
        options[8][0] = "Jupiter";
        options[8][1] = "Neptune";
        options[8][2] = "Earth";
        options[8][3] = "Mars";
        answers[8] = 1;

        questions[8] = "How many Presidents have there been of the USA? (Updated 2014)";
        options[8][0] = "36";
        options[8][1] = "29";
        options[8][2] = "46";
        options[8][3] = "44";
        answers[8] = 4;

        questions[9] = "What colour is Cerulean?";
        options[9][0] = "Red";
        options[9][1] = "Blue";
        options[9][2] = "Yellow";
        options[9][3] = "Green";
        answers[9] = 2;

        questions[10] = "What are a group of Dolphins called?";
        options[10][0] = "School";
        options[10][1] = "Herd";
        options[10][2] = "Pod";
        options[10][3] = "Pool";
        answers[10] = 3;

        questions[11] = "Who invented Penicillin?";
        options[11][0] = "Alexandra Fleming";
        options[11][1] = "Thomas Edison";
        options[11][2] = "Marie Curie";
        options[11][3] = "George Orwell";
        answers[11] = 1;


        updateDatabase();
    }

    public void updateDatabase(){
        for (int i = 0; i < numQuestions; i++)
            addQuestions(questions[i] , options[i], answers[i]);
    }

    public void addQuestions(String que, String[] ops, int ans) {
        Options options = new Options();
        options.setOptions(ops);
        options.answer = ans;

        mQuestionsReference.child(que).setValue(options);
    }

}
