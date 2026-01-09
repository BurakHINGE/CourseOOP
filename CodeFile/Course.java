public class Course {
    
    private String department;
    private String name;
    private int credits;
    private int prereqYear;
    private int maxEnrollment;
    private int reservedSeats;
    private Student[] studentList;
    private Student[] replacementList;
    private Faculty instructor;

    public Course(String department, String name, int credits, int prereqYear, int maxEnrollment, int reservedSeats) {
        this.department = department;
        this.name = name;
        this.credits = credits;
        this.prereqYear = prereqYear;
        this.maxEnrollment = maxEnrollment;
        this.reservedSeats = reservedSeats;
        this.studentList = new Student[0];
        this.replacementList = new Student[0];
    }

    public RegisterInfo registerCourse(Student std) {
        RegisterInfo info = new RegisterInfo(this);


        if (std.getStudentYear() < this.prereqYear) {
            info.setResult("REJECTED");
            info.setRegisterMessage("Request Rejected - Prerequisite");
            return info;
        }

        if (this.studentList.length >= this.maxEnrollment * 0.7 && !std.getStudentMajor().equals(this.department)) {
            info.setResult("REJECTED");
            info.setRegisterMessage("Request Rejected - Department");
            return info;
        }

        if (this.studentList.length < this.maxEnrollment - this.reservedSeats) {
            addStudent(std);
            info.setResult("APPROVED");
            info.setRegisterID(this.studentList.length);
            info.setRegisterMessage("Request Approved");
            std.addRegisterInfo(info);
            return info;
        }

        else if (this.studentList.length < this.maxEnrollment) {
            addReplacement(std);
            info.setResult("WAITING");
            info.setRegisterMessage("Request Waiting - Replacement List");
            std.addRegisterInfo(info);
            return info;
        }

        else {
            info.setResult("REJECTED");
            info.setRegisterMessage("Request Rejected - Quota");
            return info;
        }

    }

    private void addStudent(Student s) {
        Student[] temp = new Student[studentList.length + 1];
        
        for (int i = 0; i < studentList.length; i++) {
            temp[i] = studentList[i];
        }
        temp[temp.length - 1] = s;

        studentList = temp;
    }

    private void addReplacement(Student s) {
        Student[] temp = new Student[replacementList.length + 1];
        
        for (int i = 0; i < replacementList.length; i++) {
            temp[i] = replacementList[i];
        }
        temp[temp.length - 1] = s;

        replacementList = temp;
    }

    public void registerReplacementList() {
        
        if (this.instructor == null) {
            System.out.println("Error: Instructor must be assigned before processing replacement list.");
            return;
        }
    
        
        for (int i = 0; i < replacementList.length; i++) {
            if (this.studentList.length < this.maxEnrollment) {
                Student candidate = replacementList[i];
                if (candidate.getStudentMajor().equals(this.department)) {
                    
                    moveToStudentList(candidate, i);
                    i--; 
                }
            }
        }
    
        
        for (int i = 0; i < replacementList.length; i++) {
            if (this.studentList.length < this.maxEnrollment) {
                Student candidate = replacementList[i];
                moveToStudentList(candidate, i);
                i--; 
            } else {
                break; 
            }
        }
    }
    
    
    private void moveToStudentList(Student std, int replacementIndex) {
        
        addStudent(std);

        for (RegisterInfo info : std.getRegisterList()) {
            if (info.getCourse() == this) {
                info.setResult("APPROVED");
                info.setRegisterMessage("REQUEST APPROVED - FROM REPLACEMENT");
            }
        }

        Student[] newReplacement = new Student[replacementList.length - 1];
        int loc = 0;
        for (int i = 0; i < replacementList.length; i++) {
            if (i != replacementIndex) {
                newReplacement[loc++] = replacementList[i];
            }
        }
        this.replacementList = newReplacement;
    }

    public AssignInfo assignInstructor(Faculty instructor, boolean force) {

        AssignInfo info = new AssignInfo(this);

        if (!instructor.getDepartmentName().equals(this.department)) {
            info.setAssignInfo("REJECTED");
            info.setAssignMessage("Department Mismatch");
            return info;
        }

        if (this.instructor != null && !force) {
            info.setAssignInfo("REJECTED");
            info.setAssignMessage("ANOTHER INSTRUCTOR HAS ALREADY ASSIGNED");
            return info;
        }

        this.instructor = instructor;
        info.setAssignInfo("APPROVED");
        info.setAssignMessage("Instructor " + instructor.getName() + " get assigned");
        instructor.addAssignInfo(info);
        return info;
    }

    public void printStudentList() {
        for (Student s: studentList) {
            System.out.println(s.getStudentName() + " " + s.getStudentSurname());
        }
    }


    public String getCourseDepartment() {
        return department;
    }

    public String getCourseName() {
        return name;
    }

    public int getCourseCredits() {
        return credits;
    }

    public int getCourseprereqYear() {
        return prereqYear;
    }

    public int getCourseMaxEnrollment() {
        return maxEnrollment;
    }

    public int getCourseReservedSeats() {
        return reservedSeats;
    }

    public void setCourseDepartment(String department) {
        this.department = department;
    }

    public void setCourseName(String name) {
        this.name = name;
    }

    public void setCourseCredits(int credits) {
        this.credits = credits;
    }

    public void setCourseprereqYear(int prereqYear) {
        this.prereqYear = prereqYear;
    }

    public void setCourseMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public void setCourseReservedSeats(int reservedSeats) {
        this.reservedSeats = reservedSeats;
    }
}