package client.utility;

import client.AppClient;
import common.data.*;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotInDeclaredLimitsException;
import client.utility.Outputer;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Asks user a study group's value.
 */
public class StudyGroupAsker {
    private final int MAX_Y = 262;
    private final int MIN_STUDENTS_COUNT = 1;
    private final int MIN_EXPELLED_MARKS = 1;
    private final int MIN_AVERAGE_MARK = 1;
    private final long MIN_X = -438;
    private final long MIN_WEIGHT = 1;
    private Scanner userScanner;
    private boolean fileMode;

    public StudyGroupAsker(Scanner userScanner) {
        this.userScanner = userScanner;
        fileMode = false;
    }

    /**
     * Sets marine asker mode to 'File Mode'.
     */
    public void setFileMode() {
        fileMode = true;
    }

    /**
     * Sets marine asker mode to 'User Mode'.
     */
    public void setUserMode() {
        fileMode = false;
    }

    /**
     * Asks a user the group's name.
     *
     * @return Group's name.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askName() throws IncorrectInputInScriptException {
        String name;
        try {
            Outputer.println("EnterName");
            Outputer.print(AppClient.PS2);
            name = userScanner.nextLine().trim();
            Outputer.println(name);
            if (name.equals("")) throw new MustBeNotEmptyException();
            return name;
        } catch (NoSuchElementException exception) {
            Outputer.printerror("NameNotIdentifiedException");
        } catch (MustBeNotEmptyException exception) {
            Outputer.printerror("NameEmptyException");
        } catch (IllegalStateException exception) {
            Outputer.printerror("UnexpectedException");
            OutputerUI.error("UnexpectedException");
            System.exit(0);
        }
        throw new IncorrectInputInScriptException();
    }

    /**
     * Asks a user the Group's X coordinate.
     * @return Group's X coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public long askX() throws IncorrectInputInScriptException {
        String strX;
        long x;

            try {
                Outputer.println("EnterX");
                Outputer.print(AppClient.PS2);
                strX = userScanner.nextLine().trim();
                Outputer.println(strX);
                x = Long.parseLong(strX);
               return x;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("XNotIdentifiedException");
            } catch (NumberFormatException exception) {
                Outputer.printerror("XMustBeNumberException");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException!");
                System.exit(0);
            }
        throw new IncorrectInputInScriptException();
    }

    /**
     * Asks a user the Group's Y coordinate.
     * @return Group's Y coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public long askY() throws IncorrectInputInScriptException {
        String strY;
        long y;

            try {
                Outputer.println("EnterY", String.valueOf(MAX_Y+1));
                Outputer.print(AppClient.PS2);
                strY = userScanner.nextLine().trim();
                Outputer.println(strY);
                y = Long.parseLong(strY);
                return y;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("YNotIdentifiedException");
            } catch (NumberFormatException exception) {
                Outputer.printerror("YMustBeNumberException");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                OutputerUI.error("UnexpectedException");
                System.exit(0);
            }
            throw new IncorrectInputInScriptException();
        }

    /**
     * Asks a user the Group's coordinates.
     * @return Group's coordinates.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        double x;
        long y;
        x = askX();
        y = askY();
        return new Coordinates(x, y);
    }

    /**
     * Asks a user the studentsCount .
     * @return studentsCount.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public int askStudentsCount() throws IncorrectInputInScriptException {
        String strStudentsCount;
        int studentsCount;

            try {
                Outputer.println("EnterStudentsCount");
                Outputer.print(AppClient.PS2);
                strStudentsCount = userScanner.nextLine().trim();
                Outputer.println(strStudentsCount);
                studentsCount = Integer.parseInt(strStudentsCount);
                return studentsCount;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("StudentsCountNotIdentifiedException");
            } catch (NumberFormatException exception) {
                Outputer.printerror("StudentsCountMustBeMoreZero");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                OutputerUI.error("UnexpectedException");
                System.exit(0);
            }
            throw new IncorrectInputInScriptException();
        }

    /**
     * Asks a user the expelledStudents.
     * @return expelledStudents.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public int askExpelledStudents() throws IncorrectInputInScriptException {
        String strExpelledStudents;
        int expelledStudents;

            try {
                Outputer.println("EnterExpelledStudents");
                Outputer.print(AppClient.PS2);
                strExpelledStudents = userScanner.nextLine().trim();
                if (fileMode) Outputer.println(strExpelledStudents);
                expelledStudents = Integer.parseInt(strExpelledStudents);
                return expelledStudents;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("ExpelledStudentsNotIdentifiedException");
            } catch (NumberFormatException exception) {
                Outputer.printerror("ExpelledStudentsMustBeMoreZero");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                OutputerUI.error("UnexpectedException");
                System.exit(0);
            }
                throw new IncorrectInputInScriptException();
            }
    public int askAverageMark() throws IncorrectInputInScriptException {
        String strAverageMark;
        int averageMark;

            try {
                Outputer.println("EnterAverageMark");
                Outputer.print(AppClient.PS2);
                strAverageMark = userScanner.nextLine().trim();
                Outputer.println(strAverageMark);
                averageMark = Integer.parseInt(strAverageMark);
                return averageMark;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("AverageMarkNotIdentifiedException");
            } catch (NumberFormatException exception) {
                Outputer.printerror("AverageMarkMustBeMoreZero");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                OutputerUI.error("UnexpectedException");
                System.exit(0);
            }
            throw new IncorrectInputInScriptException();
        }

    public FormOfEducation askFormOfEducation () throws IncorrectInputInScriptException {
        String strFormOfEducation ;
        FormOfEducation form;

            try {
                Outputer.println("FormOfEducationList", FormOfEducation.nameList());
                Outputer.println("EnterFormOfEducation");
                Outputer.print(AppClient.PS2);
                strFormOfEducation = userScanner.nextLine().trim();
                Outputer.println(strFormOfEducation);
                form = FormOfEducation.valueOf(strFormOfEducation.toUpperCase());
                return form;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("FormOfEducationNotIndentifiedException");
            } catch (IllegalArgumentException exception) {
                Outputer.printerror("NoSuchFormOfEducation");
            } catch (IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                OutputerUI.error("UnexpectedException");
                System.exit(0);
            }
                    throw new IncorrectInputInScriptException();
                }

    /**
     * Asks a user the Group's admin .
     * @return Group's admin.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askPersonName() throws IncorrectInputInScriptException {
        String personName;
        String personNamee;

            try {
                Outputer.println("EnterPersonName");
                Outputer.print(AppClient.PS2);
                personNamee = userScanner.nextLine().trim();
                personName = personNamee;
                Outputer.println(personName);
                return personName;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("PersonNameNotIdentifiedException");
            } catch (IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                OutputerUI.error("UnexpectedException");
                System.exit(0);
            }
                    throw new IncorrectInputInScriptException();
                }


    /**
     * Asks a user the Person's weight.
     * @return Person's weight.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public long askWeight() throws IncorrectInputInScriptException {
        String strWeight;
        long weight;

            try {
                Outputer.println("EnterWeight");
                Outputer.print(AppClient.PS2);
                strWeight = userScanner.nextLine().trim();
               Outputer.println(strWeight);
                weight = Long.parseLong(strWeight);
                return weight;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("WeightCountNotIdentifiedException");
            } catch (NumberFormatException exception) {
                Outputer.printerror("WeightCountMustBeNumberException");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                OutputerUI.error("UnexpectedException");
                System.exit(0);
            }
        throw new IncorrectInputInScriptException();
    }

    public Color askHairColor() throws IncorrectInputInScriptException {
        Color hairColor;
String strHairColor;
            try {
                Outputer.println("HairColorList", Color.nameList());
                Outputer.println("EnterHairColor");
                Outputer.print(AppClient.PS2);
                strHairColor = userScanner.next().trim();
                Outputer.println(strHairColor);
                hairColor = Color.valueOf(strHairColor.toUpperCase());
                return hairColor;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("HairColorNotIdentifiedException");
            } catch (IllegalArgumentException exception) {
                Outputer.printerror("NoSuchHairColor");
            } catch (IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                OutputerUI.error("UnexpectedException");
                System.exit(0);
            }
        throw new IncorrectInputInScriptException();
    }

    public Country askNationality() throws IncorrectInputInScriptException {
        Country nationality;
        String strNationality;
            try {
                Outputer.println("NationalityList", Country.nameList());
                Outputer.println("EnterNationality");
                Outputer.print(AppClient.PS2);
                strNationality = userScanner.next().trim();
                Outputer.println(strNationality);
                nationality = Country.valueOf(strNationality.toUpperCase());
                return nationality;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("NationalityNotIdentifiedException");
            } catch (IllegalArgumentException exception) {
                Outputer.printerror("NoSuchNationality");
            } catch (IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                OutputerUI.error("UnexpectedException");
                System.exit(0);
            }
        throw new IncorrectInputInScriptException();
    }


    /**
     * Asks a user Person's info.
     * @return Person's info.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Location askLocation() throws IncorrectInputInScriptException {
        Double x = askXLocation(); //Поле не может быть null
        long y = askYLocation();
        Double z = askZLocation(); //Поле не может быть null
        String name = askNameLocation(); //Поле может быть null
        return new Location(x,y,z, name);
    }
    public String askNameLocation() throws IncorrectInputInScriptException {
        String name;
            try {
                Outputer.println("EnterNameLocation");
                Outputer.print(AppClient.PS2);
                name = userScanner.nextLine().trim();
                Outputer.println(name);
                return name;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("NameLocationNotIdentifiedException");
            } catch (IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                OutputerUI.error("UnexpectedException");
                System.exit(0);
            }
        throw new IncorrectInputInScriptException();
    }
    public Double askXLocation() throws IncorrectInputInScriptException {
        String strXLocation;
        Double x;
            try {
                Outputer.println("EnterXLocation");
                Outputer.print(AppClient.PS2);
                strXLocation = userScanner.nextLine().trim();
                Outputer.println(strXLocation);
                x = Double.parseDouble(strXLocation);
                return x;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("XLocationCountNotIdentifiedException");
            } catch (NumberFormatException exception) {
                Outputer.printerror("XLocationMustBeNumberException");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                OutputerUI.error("UnexpectedException");
                System.exit(0);
            }
        throw new IncorrectInputInScriptException();
    }

    public Double askZLocation() throws IncorrectInputInScriptException {
        String strZLocation;
        Double z;
            try {
                Outputer.println("EnterZLocation");
                Outputer.print(AppClient.PS2);
                strZLocation = userScanner.nextLine().trim();
                Outputer.println(strZLocation);
                z = Double.parseDouble(strZLocation);
                return z;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("ZLocationCountNotIdentifiedException");
            } catch (NumberFormatException exception) {
                Outputer.printerror("ZLocationMustBeNumberException");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                OutputerUI.error("UnexpectedException");
                System.exit(0);
            }
        throw new IncorrectInputInScriptException();
    }
    /**
     * Asks a user the Group's Y coordinate.
     * @return Group's Y coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public long askYLocation() throws IncorrectInputInScriptException {
        String strYLocation;
        long y;
            try {
                Outputer.println("EnterYLocation");
                Outputer.print(AppClient.PS2);
                strYLocation = userScanner.nextLine().trim();
               Outputer.println(strYLocation);
                y = Long.parseLong(strYLocation);
                return y;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("YLocationNotIdentifiedException");
            } catch (NumberFormatException exception) {
                Outputer.printerror("YLocationMustBeNumberException");
            } catch (NullPointerException | IllegalStateException exception) {
                Outputer.printerror("UnexpectedException");
                OutputerUI.error("UnexpectedException");
                System.exit(0);
            }
        throw new IncorrectInputInScriptException();
    }

    public Person askPerson() throws IncorrectInputInScriptException {
        String name;
        long weight;
        Color hairColor;
        Country nationality;
        Location location;
        name = askPersonName();
        weight = askWeight();
        hairColor = askHairColor();
        nationality = askNationality();
        location = askLocation();
        return new Person(name, weight,hairColor,nationality, location);
    }
    /**
     * Asks a user a question.
     *
     * @param question A question.
     * @return Answer (true/false).
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public boolean askQuestion(String question) throws IncorrectInputInScriptException {
        String answer;
        try {
            Outputer.print(question);
            Outputer.println(" (+/-):");
            Outputer.print(AppClient.PS2);
            answer = userScanner.nextLine().trim();
            Outputer.println(answer);
            if (!answer.equals("+") && !answer.equals("-")) throw new NotInDeclaredLimitsException();
            return answer.equals("+");
        } catch (NoSuchElementException exception) {
            Outputer.printerror("AnswerNotIndentifiedException");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("AnswerLimitsException");
        } catch (IllegalStateException exception) {
            Outputer.printerror("UnexpectedException");
            OutputerUI.error("UnexpectedException");
            System.exit(0);
        }
        throw new IncorrectInputInScriptException();
    }
}
