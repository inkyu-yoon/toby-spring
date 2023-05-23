# ✍🏻 기록

## 스프링

오브젝트를 어떻게 효과적으로 설계 · 구현 · 사용 · 개선해야하는지에 대한 기준을 마련해준다.

<details>

<summary><h2> Chapter 1. 오브젝트와 의존관계 </h2></summary>

<details>

<summary><h3> 관심사의 분리 </h3></summary>

소프트웨어 개발에서 끝이란 개념은 없다.

사용자의 비즈니스 프로세스와 그에 따른 요구사항은 끊임없이 바뀌고 발전한다.

그래서 객체를 설계할 때, ❓***어떻게 하면 변화의 폭을 최소한으로 줄일 수 있을지 고려*** 해야한다.

<br>

변화는 보통 한 가지 관심에 대해 일어나고, 💡***우리가 객체를 설계할 때 관심이 같은 것끼리는 모으고 관심이 다른 것은 분리***하면

변화로부터 영향받는 범위를 줄일 수 있다.

왜냐하면 한 가지 관심에 대한 변경이 일어날 경우, 그 관심이 집중되는 코드만 수정하면 관심이 다른 코드에는 영향을 주지않고 간단하게 수정할 수 있기 때문이다.

<br>

관심사를 가장 효과적으로 분리하는 방법은, 분리할 기능을 인터페이스로 분리한 뒤 기능을 제공하는 방법이다.

구체적인 기능 동작 원리나 방법은 구현하는 클래스가 결정하고, 외부에서 구현 클래스를 주입해주면 다형성에 의해 구현된 기능을 사용할 수 있게 된다.

<br>

이는 인터페이스를 의존하도록 하고, 인터페이스를 파라미터로 받는 생성자를 통해 의존하는 인터페이스를 초기화하고 

인터페이스에서 정의된 메서드를 사용하는 것으로 해결할 수 있다. 

그리고 제 3의 오브젝트가 어떤 구현 클래스를 사용할지 결정해서 객체 생성시 주입시켜주는💡 ***DI(Dependency Injection)를*** 해주면 된다.  

오브젝트가 직접 자신이 사용할 오브젝트를 생성해서 사용하는 경우에는, 

인터페이스로 분리했더라도 구현된 기능을 사용하기 위해 구현 클래스를 초기화하기 때문이다.

  <br>

따라서, **구현 클래스를 외부에서 결정하고 생성해서 인터페이스를 의존하고 있던(사용하고 있던) 오브젝트에 런타임 시점에서 연결해주면 강한 의존성을 끊을 수 있으며 코드가 변화에 유연해질 수 있다.** 
  
이것이 DI 라고 하고, 이 역할을 스프링에서는 IoC 컨테이너가 담당한다.

<br>

🌟 ***높은 응집도 (관심사가 비슷한 것끼리 모음)와*** ***낮은 결합도(변화하기 쉬운 구현 클래스가 아닌 변화하기 어려운 인터페이스에 의존)는*** 변화와 확장에 유연하게 대처할 수 있게 도와준다.

<br>



> 📌 좋은 객체 지향 설계 5원칙 (SOLID)
>
> 1. SRP (Single Responsibility Principle) : 단일 책임 원칙, 한 클래스는 하나의 책임만 가져야 한다. 변경이 발생할 때, 파급효과가 적으면 SRP 원칙을 잘 따른 것
> 2. OCP (Open/Closed Principle) : 개방 · 폐쇄 원칙, 확장에는 열려(개방)있고 변화에는 닫혀(폐쇄)있어야 한다. 클래스에 기능을 추가할때, 기존 코드를 변경하지않고 확장할 수 있어야 한다. 
> 3. LSP (Liskov Substitutuion Principle) : 리스코프 치환 원칙, 하위클래스는 상위클래스에서 정의한 행동 규약을 위반하지 않아야 한다.
> 4. ISP (Interface Segregation Principle) : 인터페이스 분리 원칙, 자신이 사용하지 않는 메서드에 의존관계를 맺지 않아야 하고 더 작은 인터페이스 여러개로 분리해서 사용하는 것이 좋다.
> 5. DIP (Dependency Inversion Principle) : 의존관계 역전 원칙, 변화하기 쉬운 구현 클래스에 의존하지 말고 인터페이스에 의존하는 것이 좋다.

  </details>
  
  <details>

<summary><h3> 프레임워크와 라이브러리의 차이 </h3></summary>

**라이브러리**를 사용하는 코드는 **개발자의 코드가 애플리케이션 흐름을 주도**하고, 동작하는 중에 필요할 때 능동적으로 라이브러리를 사용한다.

반면에, **프레임워크**는 **프레임워크가 애플리케이션의 흐름을 주도**하고, 동작하는 중에 개발자가 만든 코드를 사용하여 수동적으로 동작하게 된다.

프레임워크는 제어의 역전 개념이 적용되어 있어야 한다.



</details>
  <details>
<summary><h3>  스프링이 싱글톤으로 빈을 관리하는 이유?   </h3></summary>

대규모 엔터프라이즈 서버 환경에서 매번 클라이언트가 요청할때 마다 로직을 담당하는 오브젝트를 새로 생성해서 사용하면, 서버 부하가 심해질 것이다.  
    
따라서 하나의 오브젝트를 생성해두고, 공유해서 사용하는 것이다.
  
싱글톤은 자바에서 다음과 같은 방식으로 구현할 수 있다.
  
```java
  pubilc class Singleton{
    private static Singleton INSTANCE;
  
    //외부에서 객체를 생성하지 못하도록 private 접근 제어자를 붙인 생성자를 정의한다.
    private Singleton(){
    }
  
    // INSTANCE가 null 인 경우에만 객체를 새로 생성하고, 이미 존재하는 경우에는, 존재하는 인스턴스를 반환한다.
    public static synchronized Singleton getInstance(){
      if (INSTANCE == null){
        INSTANCE = new Singleton();
      }
      return INSTANCE;
    }
  }
```
    
🚨 private 생성자를 사용하면 객체지향의 장점인 상속을 사용할 수 없고 전역 변수로 사용되어 Mock 주입이 어려워 테스트 하기 힘들다는 단점이 있다.

따라서 자바에서의 구현 방식은 여러가지 단점이 있기 때문에,   
    
스프링에서 제공하는 **싱글톤 레지스트리**를 사용하여 💡 static 메서드나 private 생성자를 사용하는 클래스가 아닌 평범한 자바 클래스를 싱글톤으로 활용할 수 있게 해준다.


</details>

<details>

<summary><h3>  💡 용어 정리 </h3></summary>

1. 📖 ***DAO (Data Access Object)*** : DB를 사용해 **데이터를 조회 · 조작하는 기능을 담당하는 오브젝트**
2. 🫘***자바빈 규약이란?*** : 파라미터가 없는 생성자가 있고 `get()` 과 `set()` 메서드가 정의되어 있어 속성을 수정하거나 조회할 수 있다.
3. 🏭 ***팩토리 메서드 패턴*** : 상위 클래스에서는 인터페이스 형태의 오브젝트를 생성하는 추상 메서드를 정의해서 사용하고, **서브 클래스에서 구체적인 오브젝트 생성 방법을 결정하게 하는 방법**을 말한다. 
4. 🪄 ***전략 패턴*** : 독립적인 책임으로 분리가 가능한 기능을 개별적인 **인터페이스로 분리**한 뒤, 이를 구현한 클래스를 **필요에 따라 바꿔서 사용할 수 있도록 하는 방법**
5. 🔃 ***제어의 역전 (IoC : Inversion of Control)*** : 자신이 사용할 오브젝트를 직접 결정하고 생성하는 것이 아닌, **제어권을 가진 다른 오브젝트에 의해 결정**된다. 
6. 🌞 ***애플리케이션 컨텍스트 (Application Context)*** : IoC 방식으로 빈을 관리하는 빈 팩토리를 상속받아, **빈 관리 뿐만 아니라 스프링이 제공하는 다른 부가기능을 이용해 제어작업을 총괄하는 오브젝트**이다. 오브젝트가 만들어지는 방식, 시점과 전략을 다르게 가져갈 수 있고 자동생성, 오브젝트에 대한 후처리, 인터셉팅 등 다양한 기능을 할 수 있다.


</details>
  
  </details>
