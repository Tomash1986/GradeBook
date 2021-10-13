package com.gradebook_09_2021.gradebook_09_2021.utils.studentFactory;


import com.gradebook_09_2021.gradebook_09_2021.model.common.ClassSign;
import com.gradebook_09_2021.gradebook_09_2021.model.common.Subject;
import com.gradebook_09_2021.gradebook_09_2021.model.common.YearBook;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Grade;
import com.gradebook_09_2021.gradebook_09_2021.model.studDomain.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SchoolFactory {

    private Random random= new Random();
    private Integer idStudent=0;

    // Założenia
    // Trzeba stowrzyc szkołę na okolo 120 uczniów
    // Musi byc
    // zkałdam że będze to mała szkola i będzie po 4 klasy na rocznik
    public List<Student> getSchoolStudentList(){
        List<Student> schoolStudList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {

            schoolStudList.add(getRandStudent(YearBook.I, ClassSign.a));
        }
        for (int i = 0; i < 25; i++) {

            schoolStudList.add(getRandStudent(YearBook.I, ClassSign.b));
        }
        for (int i = 0; i < 27; i++) {

            schoolStudList.add(getRandStudent(YearBook.I, ClassSign.c));
        }

        for (int i = 0; i < 24; i++) {

            schoolStudList.add(getRandStudent(YearBook.II, ClassSign.a));
        }
        for (int i = 0; i < 21; i++) {

            schoolStudList.add(getRandStudent(YearBook.II, ClassSign.b));
        }
        for (int i = 0; i < 26; i++) {

            schoolStudList.add(getRandStudent(YearBook.II, ClassSign.c));
        }

        for (int i = 0; i < 21; i++) {
            schoolStudList.add(getRandStudent(YearBook.III, ClassSign.a));
        }
        for (int i = 0; i < 23; i++) {
            schoolStudList.add(getRandStudent(YearBook.III, ClassSign.b));
        }
        for (int i = 0; i < 24; i++) {
            schoolStudList.add(getRandStudent(YearBook.III, ClassSign.c));
        }

        return schoolStudList;
    }



    // Tak trzeba zrobic ponieważ imiona nazwiska muszą być przypadkowe oraz ocenu ale musze byc dla duze grupy identyczne rocznki i sygnatury klas
    public Student getRandStudent(YearBook yearBook, ClassSign classSign){
        return new Student(getStudRandName(),getStudRandSurName(),yearBook,classSign,getStudRandGradesBoook());
    }

    private List<Grade> getStudRandGradesBoook(){
        List<Grade> studRandGradesBook = new ArrayList<>();
        getStudRandomGrades(studRandGradesBook, Subject.POL);
        getStudRandomGrades(studRandGradesBook, Subject.MAT);
        getStudRandomGrades(studRandGradesBook, Subject.ANG);
        return studRandGradesBook;
    }



    private void getStudRandomGrades(List<Grade> gradesList, Subject subject) {
        Integer[] integers1={1,2,3,4,5,6,3,4,2,3};
        Integer[] integers2={1,2,3,3,5,1,3,1,2,3};
        Integer[] integers3={4,4,3,3,5,3,3,5,4,3};
        int numbOfGrades = random.nextInt(3);
        if(numbOfGrades==0){
            for (int i = 0; i <5 ; i++) {
                Grade grade = new Grade(integers1[random.nextInt(integers1.length)],subject, LocalDate.now());
                gradesList.add(grade);
            }
        }
        else if(numbOfGrades==1){
            for (int i = 0; i <8 ; i++) {
                Grade grade = new Grade(integers2[random.nextInt(integers2.length)],subject, LocalDate.now());
                gradesList.add(grade);
            }
        }
        else if(numbOfGrades==2){
            for (int i = 0; i <6 ; i++) {
                Grade grade = new Grade(integers3[random.nextInt(integers2.length)],subject, LocalDate.now());
                gradesList.add(grade);
            }
        }
    }


    private String getStudRandName(){
        String[] arrOfName_1 = {"Tomasz", "Marcin", "Andrzej", "Michał", "Piotr", "Jan", "Emilia", "Magda","Krzysztof","Zenon"};
        String[] arrOfName_2 = {"Andżelika", "Monika", "Janusz", "Michał", "Anna", "Justyna", "Elżbieta", "Magda","Krzysztof", "Dominika"};
        String[] arrOfName_3 = {"Anna", "Dariusz", "Andrzej", "Michał", "Piotr", "Justyna", "Grzegorz", "Magda","Władysława","Alicja"};
        String[] arrOfName_4 = {"Roman", "Marcin", "Robert", "Waldemar", "Piotr", "Jan", "Emilia", "Paweł","Władysława","Agnieszka"};
        String[] arrOfName_5 = {"Łukasz", "Aleksandra", "Robert", "Waldemar", "Tomasz", "Julian", "Antoni", "Paweł","Władysław","Maria"};
        String[] arrOfName_6 = {"Weronika", "Aleksandra", "Wiktoria", "Kacper", "Wiktor", "Joanna", "Antoni", "Joanna","Mikołaj", "Mariusz"};
        String[] arrOfName_7= {"Barbara", "Wojciech", "Rafał", "Monika", "Radosław", "Zbigniew", "Bogusław", "Jacek","Edward","Artur"};
        int randNum = random.nextInt(7);
        if(randNum==0){
            return arrOfName_1[random.nextInt(arrOfName_1.length)];
        }
        else if(randNum==1){
            return arrOfName_2[random.nextInt(arrOfName_2.length)];
        }
        else if(randNum==2){
            return arrOfName_3[random.nextInt(arrOfName_3.length)];
        }
        else if(randNum==3){
            return arrOfName_4[random.nextInt(arrOfName_4.length)];
        }
        else if(randNum==4){
            return arrOfName_5[random.nextInt(arrOfName_5.length)];
        }
        else if(randNum==5){
            return arrOfName_6[random.nextInt(arrOfName_6 .length)];
        }
        else
            return arrOfName_7[random.nextInt(arrOfName_7.length)];
    }

    private String getStudRandSurName(){
        String[] arrOfSurName_1 ={"Włodarczyk","Sikora","Parada","Adamczyk","Zaremba","Duda","Olszewski/ka","Traczyk","Milewski/ka", "Jaśiński/ka","Owczarek"};
        String[] arrOfSurName_2 ={"Włodarczyk","Nadzieja","Zieliński/ka","Wójcik","Nowak","Szymański/ka","Olszewski/ka","Traczyk","Nowak", "Głowacki/ka","Kaczyśki/ka"};
        String[] arrOfSurName_3 ={"Kowalski/ka","Wolski/ka","Zieliński/ka","Wójcik","Adamczuk","Szymański/ka","Olszewski/ka","Traczyk","Kaczmarek", "Głowacki/ka","Kaczyński/ka"};
        String[] arrOfSurName_4 ={"Kowalski/ka","Biernat","Zieliński/ka","Marczecki/ka","Kopacki/ka","Pieńkowski/ka","Węglarczyk","Miler","Charczuk", "Olewiński","Kaczyński/ka"};
        String[] arrOfSurName_5 ={"Kowalczyk","Biernat","Kowalczuk","Strus","Piotrowicz","Prońko","Prończuk","Miler","Grodzki/ka", "Owerczuk","Walewski/ka"};
        String[] arrOfSurName_6 ={"Borewicz","Miodowicz","Walczuk","Staniaszek","Piotrowicz","Biedroń","Prończuk","Cholecki/ka","Grodzki/ka", "Kropiewnicki/ka","Radula"};

        int randNum = random.nextInt(7);
        if(randNum==0){
            return arrOfSurName_1[random.nextInt(arrOfSurName_1.length)];
        }
        else if (randNum==1){
            return arrOfSurName_2[random.nextInt(arrOfSurName_2.length)];
        }
        else if (randNum==2){
            return arrOfSurName_3[random.nextInt(arrOfSurName_3.length)];
        }
        else if (randNum==3){
            return arrOfSurName_4[random.nextInt(arrOfSurName_4.length)];
        }
        else if (randNum==4){
            return arrOfSurName_5[random.nextInt(arrOfSurName_5.length)];
        }
        else if (randNum==5){
            return arrOfSurName_6[random.nextInt(arrOfSurName_6.length)];
        }
        else
            return arrOfSurName_1[random.nextInt(arrOfSurName_6.length)];
    }

}
