package entity;

public class Employee {
    private int id;
    private String name;
    private int sal;

    public Employee(int id, String name, int sal){
        this.id=id;
        this.name=name;
        this.sal=sal;
    }

  public int getId(){
        return id;
  }

  public String getName(){
        return name;
  }

  public int getSal(){
        return sal;
  }

  public void setId(int id){
        this.id=id;
  }

  public void setName(String name){
        this.name=name;
  }

  public void setSal(int sal){
        this.sal=sal;
  }
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sal=" + sal +
                '}';
    }
}
