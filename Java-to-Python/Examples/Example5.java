Class Dog{
  String name;
  String breed;
  int age;

Dog(String name, String breed, int age){
      this.name = name;
      this.breed = breed;
      this.age = age;
      this.color = color;
  }

  public String getName(){
      return name;
  }

  public String getBreed(){
        return breed;
  }

  public int getAge(){
        return age;
  }

  public boolean getAll(){
      getName();
      getBreed();
      getAge();

  }
}