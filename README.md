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

이는 인터페이스를 의존하도록 하고, 인터페이스를 파라미터로 받는 생성자를 통해 의존하는 인터페이스에 사용할 구현 오브젝트를 할당한 뒤 

인터페이스에서 정의된 메서드를 사용하는 것으로 해결할 수 있다. 

런타임 시점에서 두 오브젝트를 연결하는 작업은, 제 3의 오브젝트가 어떤 구현 클래스를 사용할지 결정해서 객체 생성시 주입시켜주는💡 ***DI(Dependency Injection)를*** 해주면 된다.  


  <br>

따라서, **구현 클래스를 외부에서 결정하고 생성해서 인터페이스를 의존하고 있던(사용하고 있던) 오브젝트에 런타임 시점에서 연결해주면 강한 의존성을 끊을 수 있으며 코드가 변화에 유연해질 수 있다.** 
  
이한러한 역할을 스프링에서는 **IoC 컨테이너**가 담당한다.

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

대규모 엔터프라이즈 서버 환경에서 **매번 클라이언트가 요청할때 마다 로직을 담당하는 오브젝트를 새로 생성해서 사용**하면, 서버 부하가 심해질 것이다.  
    
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


<details>

<summary><h2> Chapter 2. 테스트 </h2></summary>

<details>

<summary><h3> 단위 테스트 (Unit Test) 를 하는 이유</h3></summary>

작은 단위의 코드에 대해 테스트를 수행한 것을 **단위 테스트**라고 한다.

한꺼번에 너무 많은 것을 몰아서 테스트하면 테스트 수행 과정도 복잡해지고, **오류가 발생했을 때 어떤 부분에서 오류가 발생했는지 정확한 원인을 쉽게 찾기 힘들어진다.**

<br>

단위 테스트는 항상 일관성 있는 결과가 보장돼야 한다.

💡 외부 환경에 영향을 받지 말아야 하는 것은 물론이고 테스트를 실행하는 순서를 바꿔도 동일한 결과가 보장되도록 만들어야 한다.


  </details>

  <details>

<summary><h3> 테스트 주도 개발 (TDD : Test Driven Development) </h3></summary>

만들고자 하는 기능의 내용을 담고 있으면서 만들어진 코드를 검증도 해줄 수 있도록 테스트 코드를 먼저 만들고, 테스트를 성공하게 해주는 코드를 작성하는 방식이다.

***실패한 테스트를 성공시키기 위한 목적이 아닌 코드는 만들지 않는다*** 는 것이 기본 원칙이다.

<br>

TDD의 장점 중 하나는 코드를 만들어 테스트를 실행하는  그 사이의 간격이 매우 짧다는 점이다.

테스트 없이 오랜 시간 동안 코드를 만들고 나서 테스트를 하면, 오류가 발생했을 때 원인을 찾기 쉽지 않다.

</details>

  <details>

<summary><h3> 테스트 용 설정 정보(application-test.yml) 사용 </h3></summary>


```
spring:
  profiles:
    active: test
```

기존에 사용하던 `application.yml` 파일에 위와 같은 구문을 추가해준다.

<br>

그리고, 테스트 용으로 사용할 설정 정보가 담긴 `application-test.yml` 파일을 생성한다.

💡 `application-{별칭}.yml` 과 `spring.profiles.active: {별칭}` 의 별칭은 동일해야 한다.

<br>

그리고, 테스트를 실행할 클래스에

```
@SpringBootTest
@ActiveProfiles("test")
```

혹은

```
@SpringBootTest(properties = "spring.profiles.active=test")
```

와 같이 어노테이션을 적용하면, `application-test.yml`에 적용한 환경변수로 테스트를 실행할 수 있다.



</details>



<details>

<summary><h3>  💡 용어 정리 </h3></summary>

1. 📖 픽스처(fixture) : 테스트를 수행하는데 필요한 정보나 오브젝트
2. 💩`@DirtiesContext` : 어플리케이션 컨텍스트의 상태를 테스트 코드 내에서 수동으로 변경하는 경우 (클래스 레벨 · 메서드 레벨) 에 사용한다. **변경한 컨텍스트가 다른 테스트에 영향을 주지 않게** 하기 위해, **테스트 메서드를 수행하고 나면 매번 새로운 어플리케이션 컨텍스트를 만들어서 다음 테스트가 사용**하게 해준다.
3. ⛑️ 비침투적 기술(noninvasive) : 기술에 종속적이지 않은 **순수한 코드를 유지할 수 있게 해주는 기술**, 대표적으로 **스프링이 비침투적 기술**이고 이전에 사용하던 EJB는 침투적 기술이라고 할 수 있다. 기술을 사용하기 위해 특정 인터페이스나 클래스를 사용하도록 강제하는 기술을 침투적 기술이라고 한다.

</details>

  </details>



<details>

<summary><h2> Chapter 3. 템플릿 </h2></summary>

<details>

<summary><h3> Connection과 같은 공유 리소스를 반드시 반환해야하는 이유</h3></summary>


❓ `Connection` 이나 `PreparedStatement` 를 사용하고 `close()` 메서드로 명시적으로 리소스를 반환하는 이유가 뭘까?

일반적으로 서버에서는 제한된 개수의 DB 커넥션을 만들어서 재사용 가능한 풀로 관리한다.

매번 생성하는 것 보다 미리 정해진 개수 만큼 만들어놓고 돌려가며 사용하는 방식이 유리하기 때문이다.

<br>

💡 이때, 제한된 개수의 커넥션을 가져가서 사용하고나서 명시적으로 `close()` 해서 **반환해서 돌려줘야지만, 재사용할 수 있게 된다.**

<br>

반환되지 못한 `Connection` 이 쌓이다가 커넥션 풀에 여유가 없어지면 리소스가 모자란다는 오류를 내며 서버가 중단될 수 있다.

따라서, 중간에 예외가 발생하더라도 정상적으로 가져간 리소스를 반환할 수 있도록 해야한다.

<br>

📌 그리고 리소스는 만들어진 순서의 역순으로 `close()` 해주는 것이 좋다.

```
Connection c = dataSource.getConnection();
PreparedStatement ps = c.prepareStatement("delete from users");

ps.close();
c.close();
```

위와 같은 경우, `ps.close()` 를 먼저 해주고 `c.close()` 를 해주어야 한다는 의미이다.

`Connection`을 먼저 닫는 경우 `PrepredStatement`가 활성화 된 상태로 남아있을 수 있어 리소스가 낭비될 수 있다.





  </details>
<details>

<summary><h3>  템플릿 · 콜백 패턴 </h3></summary>

전략 패턴의 기본 구조에 익명 내부 클래스를 활용한 방식이다.

템플릿은 고정된 작업 흐름을 가진 코드를 재사용한다는 의미에서 붙인 이름이다.

콜백은 템플릿 안에서 호출되는 것을 목적으로 만들어진 오브젝트이다.

<br>

일반적으로, 템플릿에서는 인터페이스의 메서드를 사용하고 콜백은 하나의 메소드를 가진 인터페이스를 구현한 익명 클래스로 만들어진다.

따라서, 💡 **콜백은 템플릿과 같은 클래스에 존재하고, 콜백이 인터페이스를 구현하는 익명 클래스를 템플릿에 전달함으로서 일종의 DI가 이루어진다.**

클래스간의 관계를 설정파일에 노출되지 않는다는 장점이 있다.

 <br>

📌 일반적인 DI는 템플릿에 인스턴스 변수를 만들어 두지만, 템플릿 · 콜백 방식에서는 매번 메소드 단위로 사용할 오브젝트를 새롭게 전달받는다는 특징이 있다. 또한, 콜백 오브젝트는 자신을 생성한 클라이언트 메소드 내의 정보를 직접 참조하여 강하게 결합되어 있다는 특징이 있다.

<br>

중복되는 코드를 메서드로 분리하고, 그 메서드가 인터페이스로 분리되고, 분리한 메서드가 고정된 작업 흐름을 갖고 작업 마다 바뀌는 부분이 존재한다면 템플릿 · 콜백 패턴을 적용하는 것을 고려할 수 있다.


</details>

<details>

<summary><h3>  네거티브 테스트의 중요성 </h3></summary>

개발자들은 수동 테스트를 할 때 실패할 만한 상황을 잘 고려하지 못하는 경우가 많다.

문제가 발생하는 경우는 주로 예외적인 조건과 결과 때문에 발생한다.

그래서 같은 개발자가 만든 메서드인데도, 어떤 메서드는 데이터가 없으면 null을 리턴하고, 어떤 메서드는 빈 리스트 오브젝트를 리턴하고, 어떤 메서드는 예외를 던지고, 어떤 메서드는 런타임 예외가 발생하면서 뻗어버린다.

💡 따라서 예외 상황에 대한 **일관성 있는 기준을 정해두고 이를 테스트로 만들어 검증**해둬야 한다.

의도적으로 예외적인 조건에 대해 테스트를 만드는 습관이 있으면 안정적인 코드를 작성할 수 있다.

</details>

  </details>


<details>

<summary><h2> Chapter 4. 예외 </h2></summary>

<details>

<summary><h3> 잘못된 예외 처리의 예시</h3></summary>

```
try{
	...
} catch(SQLException e){

}
```

🚨 예외를 잡고 아무것도 하지 않는 코드는 예외 발생을 무시하고 정상적인 상황인 것처럼 다음 라인으로 넘어가겠다는 분명한 의도가 있는 게 아니라면 연습 중에도 절대 만들어서는 안 되는 코드다.

프로그램 실행 중에 어디선가 오류가 있어서 예외가 발생해도 그것을 무시하고 계속 진행해버리기 때문이다.

<br>

예상치 못한 다른 문제를 일으킬 수 있고 문제의 원인이 무엇인지 찾아내기 매우 힘들어진다.

<br>

예외를 처리할 때 반드시 지켜야 할 핵심 원칙은 한 가지다. **모든 예외는 적절하게 복구되든지 아니면 작업을 중단시키고 운영자 또는 개발자에게 분명하게 통보돼야 한다.**


  </details>


<details>

<summary><h3> Java의 예외 종류와 특징</h3></summary>

<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/105691109-2cda9400-5f40-11eb-9003-a14873c2eaf2.png" alt="image" style="zoom: 33%;" />

- Error

  `java.lang.Error` 클래스의 서브 클래스들이다. 주로 JVM에서 발생시키는 것이고 `OutOfMemoryError`나 `ThreadDeath` 같은 에러를 발생시키기 때문에 애플리케이션 코드에서 처리하기 힘들 예외들이 있다.

- Checked Exception

  `RuntimeException`을 상속하지 않는 `java.lang.Exception` 클래스의 서브 클래스들이다. 체크 예외가 발생할 수 있는 메서드를 사용할 경우 **반드시 예외를 처리하는 코드를 catch문으로 잡든가 throws를 정의해서 메서드 밖으로 처리해야 컴파일 에러가 발생하지 않는다.** 즉, 명시적으로 예외를 처리해야한다.

- Unchecked Exception

  `RuntimeException` 클래스를 상속하는 클래스들이다. 명시적인 예외처리를 하지 않아도 컴파일에러가 발생하지 않는다. 대표적으로 오브젝트를 할당하지 안혹 사용하려고 시도했을 때, 발생하는 `NullPointerException`이 있다. 이런 예외는 개발자가 주의 깊게 만든다면 피할 수 있는, 예상하지 못했던 상황에서 발생하는 것이 아니기 때문에 명시적으로 예외처리를 하지 않아도 되도록 만든 것이다.

checked 예외 처리의 경우 예외처리를 강제하여 안정성을 높이고 명시적으로 예외를 확인할 수 있다. unchecked 예외 처리의 경우 예외처리 로직을 간결하게 작성할 수 있지만 예기치 않게 중단될 수 있어 주의가 필요하다.


  </details>

<details>
<summary><h3> JdbcTemplate 를 사용하면 왜 SQLException을 throws 하거나 명시적으로 처리하지 않는가</h3></summary>
JdbcTemplate 템플릿과 콜백 안에서 발생하는 모든 SQLException을 런타임 예외인 DataAccessException으로 포장해서 던져준다.

따라서, 필요한 경우에만 DataAccessException을 catch해서 처리하면 된다.

<br>
런타임 예외이므로 unchecked 예외이고, 따라서 호출하는 메서드에서 이를 꼭 처리해야할 의무는 없다.

<br>

JdbcTemplate의 쿼리를 전달하는 템플릿을 보면, 아래와 같이 SQLException을 런타임 예외인 DataAccessException에 포장하여 중첩예외 형태로 던지는 것을 확인할 수 있다.

```java
catch (SQLException ex) {
   // Release Connection early, to avoid potential connection pool deadlock
   // in the case when the exception translator hasn't been initialized yet.
   if (psc instanceof ParameterDisposer parameterDisposer) {
      parameterDisposer.cleanupParameters();
   }
   String sql = getSql(psc);
   psc = null;
   JdbcUtils.closeStatement(ps);
   ps = null;
   DataSourceUtils.releaseConnection(con, getDataSource());
   con = null;
   throw translateException("PreparedStatementCallback", sql, ex);
}
```

따라서, 우리가 JdbcTemplate에 있는 메서드를 사용할 때, SQLException은 템플릿에서 처리되어 호출한 메서드로 throws 하지 않기 때문에 SQLException 선언이 사라진 것이다.



  </details>


<details>
<summary><h3> 애플리케이션 예외는 Check 예외로 처리 </h3></summary>

애플리케이션 자체의 로직에 의해 의도적으로 발생시키고, 반드시 catch해서 무엇인가 조치를 취하도록 요구하는 예외를 일반적으로 애플리케이션 예외라고 한다.

예를 들어, 은행계좌에서 출금하는 기능을 가진 메서드가 있을 때, 잔고 상황에 따라 이어지는 작업이 달라져야 할 것이다.

<br>

```java
try{
    // 출금하는 메서드
} catch(CustomException e){
    // 잔고 부족시 CustomException이 발생하고, catch 문 로직 실행
    // 잔고 부족 안내 메시지 표시 등 예외 처리
}
```



따라서, 잔고 부족인 경우 비즈니스적인 의미를 띈 체크 예외를 던지게끔 하면, 예외 상황을 처리하는 로직은 catch 블록에 모아둘 수 있어 이해하기 편해진다.

그리고, check 예외기 때문에 에외를 명시적으로 처리하게끔 강제하기 때문에 안전한 코드를 만들 수 있다.

  </details>


  </details>


<details>

<summary><h2> Chapter 5. 서비스 추상화 </h2></summary>

  <details>

  <summary><h3> 회원 등급 업그레이드 예제 코드 개선해보기</h3></summary>

```java
public void upgradeLevels(){
    List<User> users = dao.getAll();
    for(User user : users){
        Boolean changed = null;
        if (user.getLevel() == Level.BASIC && user.getLogin() >=50){
            user.setLevel(Level.SILVER);
            changed = true;
        } else if (user.getLevel() == Level.SILVER && user.getRecommend() >=30){
            user.setLevel(Level.GOLD);
            changed = true;
        } else if (user.getLevel() == Level.GOLD){
            changed = false;
        } else {
            changed = false;
        }
        
        if (changed){
            dao.update(user);
        }
    }
}
```



교재에 나와 있는 회원 등급 업그레이드를 하는메서드이다.

베이직 회원인 경우, 로그인 횟수가 50 이상이면 실버 회원이 되고

실버 회원인 경우, 추천 수가 30 이상이면 골드 회원이 된다.

골드 회원이거나, 위 조건을 만족하지 못하면 회원 등급 변경은 없다.

위, 메서드를 객체 지향적인 구조로 리팩토링하고 Stream API를 활용해서 간결하게 개선해보았다.

<br>



```java 
    public void upgradeLevels() {

        List<User> users = userDao.getAll();

        users.stream()
                .filter(user -> canUpgradeLevel(user) )
                .forEach(user -> userDao.update(user.upgradeLevel()));

    }

    public boolean canUpgradeLevel(User user) {
        return user.canUpgradeLevelBasic(MIN_LOGIN_COUNT_FOR_SILVER) || user.canUpgradeLevelSilver(MIN_RECOMMEND_COUNT_FOR_GOLD);
    }
```

`getAll()` 메서드로 가져온 List에 담겨진 user들을 `filter()` 메서드로 등업 조건에 해당하는 user만 필터링한다.

등업 조건을 확인하는 메서드 역시, 생각해보면 나중에 충분히 바뀔 수 있는 로직이다.

따라서, 메서드로 분리하였고 등급 별 등업 조건이 다르므로 이 부분도 분리해주었다.

<br>

그리고 User 객체 본인의 레벨과 login 혹은 recommend 수를 통해 등업할 수 있는 조건인지 확인하고 등업 조건에 해당하는 login 수나 recommend 수는 Enum 타입으로 관리하도록 했다.

<br>

```java
public class User {
    String id;
    String name;
    String password;
    Level level;
    int login;
    int recommend;

    public User upgradeLevel() {
        this.level = Level.valueOf(this.level.getValue() + 1);
        return this;
    }
    
    public boolean canUpgradeLevelBasic(int minLoginCountForUpgrade) {
        return this.level.equals(Level.BASIC) && this.login >= minLoginCountForUpgrade;
    }

    public boolean canUpgradeLevelSilver(int minRecommendCountForUpgrade) {
        return this.level.equals(Level.SILVER) && this.recommend >= minRecommendCountForUpgrade;
    }
}
```

그리고, 필터링 된 각 user의 등급을 1 등급씩 올리고 수정된 User 본인을 반환하는 `upgradeLevel()` 메서드를 User 객체안에 정의했다.

3개의 테스트 케이스를 작성해서, 정상 동작함을 확인할 수 있었다.

<br>

객체 지향적인 코드는 다른 오브젝트의 데이터를 가져와서 작업하는 대신 데이터를 갖고 있는 다른 오브젝트에게 작업을 해달라고 요청한다.

오브젝트에게 데이터를 요구하지 말고 작업을 요청하라는 것이 객체지향 프로그래밍의 가장 기본적인 원리이다.

<br>

따라서 user의 레벨을 가져와서 service 가 대신 user.getLevel() 해서 등업 조건에 만족하는지 확인하고 user.setLevel(nextLevel) 메서드를 호출해서 등급을 변경시키는 것이 아니라

user가 갖고 있는 메서드를 통해서 user 객체 스스로가 판단하고 본인의 필드를 본인이 변경할 수 있게끔 행동을 유도하도록 코드를 작성하였다.


<br>

Stream API를 사용해서 코드도 간결해지고, 객체에게 행위를 부여하였고 변경될 수 있는 부분을 고려한 좀 더 객체 지향적인 코드로 개선할 수 있었던 것 같다.


  </details>


  <details>

  <summary><h3> 트랜잭션 동기화를 이용한 트랜잭션 통합과 예외 처리 </h3></summary>


```java
 public void upgradeLevels() {

        List<User> users = userDao.getAll();

        users.stream()
                .filter(user -> canUpgradeLevel(user) )
                .forEach(user -> userDao.update(user.upgradeLevel()));

    }
```

위 로직은 업그레이드 조건을 만족하는 사용자들을 `get` 해서 `jdbcTemplate` 의 `update` 메서드를 사용해서 등급을 변경시키는 메서드이다.

<br>



```java
    @Override
    public void update(User user) {

        this.jdbcTemplate.update("update users set name = ? , password = ? , level = ?, login = ?, " +
                        "recommend=? where id = ?",
                user.getName(), user.getPassword(), user.getLevel().getValue(), user.getLogin(), user.getRecommend(), user.getId());
    }
```

조건에 만족하는 각 `user` 마다 `jdbcTemplate.update()` 로 DB에 접근하여 데이터를 갱신시키는 작업이다.

만약, 조건에 만족하는 `users` 를 가져와서, `forEach`로 업그레이드 로직을 하던 중, 중간에 에러가 발생하면 어떻게 될까?

<br>

각 user마다 트랜잭션을 사용하기 때문에, 에러가 발생한 user만 등급 변경이 반영되어 있지 않고 이전에 변경된 user는 반영되어 있을 것이다.

<br>

따라서, 중간에 에러가 발생했을 때 이전에 변경되었던 회원도 반영되지 않도록 하려면 하나의 트랜잭션으로 묶어야 한다.

<br>

그럴때 사용할 수 있는 클래스가 `TransactionSynchronizationManager` 클래스이다.

```java
public void upgradeLevels() throws SQLException {

    	// 트랜잭션 동기화 작업을 초기화
        TransactionSynchronizationManager.initSynchronization();
    	// 커넥션을 가져옴
        Connection c = DataSourceUtils.getConnection(dataSource);
    	// 자동으로 커밋되는 설정을 false & 트랜잭션 시작
        c.setAutoCommit(false);


        try {
            List<User> users = userDao.getAll();

            users.stream()
                    .filter(user -> canUpgradeLevel(user))
                    .forEach(user -> userDao.update(user.upgradeLevel()));

            c.commit();
        } catch (Exception e) {
            // 에러 발생 시 rollback 한다.
            c.rollback();
            throw new RuntimeException(e);
        } finally {
            // db커넥션과 트랜잭션 관리 클래스를 종료한다.
            DataSourceUtils.releaseConnection(c, dataSource);
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }
    }
```

위와 같이, 한 로직이 같은 트랜잭션을 공유하도록 로직을 변경하면 된다.

<br>

❓ **트랜잭션 동기화란 무엇일까** : Connection을 특별한 저장소에 보관해두고 이후에 호출되는 메서드는 저장된 Connection을 사용하게 하는 것



<br>



`JdbcTemplate` 메서드는 가장 먼저 **트랜잭션 동기화 저장소에 현재 시작된 트랜잭션을 가진 Connection 오브젝트가 존재하는지 확인한다.**

존재해서 트랜잭션 동기화 저장소에서 가져온 Connection을 사용하는 경우에는 메서드가 종료될 때, **Connection을 닫지 않은 채로 작업을 마친다.**

그렇게 해서, 모든 로직이 끝나고 `c.commit()` 과 같이 명시적으로 커밋을 했을 때 트랜잭션이 완료된다.

`finally` 구문에서 `TransactionSynchronizationManager.unbindResource(this.dataSource);` 를 통해 트랜잭션 동기화 저장소에 있는 `dataSource`를 제거한다.





  </details>


  <details>

  <summary><h3> 글로벌 트랜잭션 인터페이스를 적용한 유연성 향상 </h3></summary>


하나의 트랜잭션 안에서 여러개의 DB에 데이터를 넣는 작업은 JDBC의 Connection을 이용한 로컬 트랜잭션 방식으로는 불가능하다.

별도의 트랜잭션 관리자를 통해 트랜잭션을 관리하는 **글로벌 트랜잭션** 방식을 사용해야한다.

<br>

글로벌 트랜잭션 관리는 JDBC와 Hibernate와 방식이 다르기 때문에 추상화되어 있는 가장 상위 클래스를 스프링은 제공한다.

이를 이용하면, 애플리케이션에서 직접 각 기술의 트랜잭션 API를 이용하지 않고도 일관된 방식으로 트랜잭션을 제어하는 작업이 가능해진다.

<br>

`PlatformTransactionManager` 라는 인터페이스가 존재하고 이를 이용해서 글로벌 트랜잭션을 이용하면 된다.

<br>

```java
    public void upgradeLevels() throws SQLException {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            List<User> users = userDao.getAll();

            users.stream()
                    .filter(user -> canUpgradeLevel(user))
                    .forEach(user -> userDao.update(user.upgradeLevel()));

            transactionManager.commit(transaction);
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            throw new RuntimeException(e);
        }
    }
```



위와 같이 JDBC의 로컬 트랜잭션을 사용하는 경우는 `PlatformTransactionManager` 인터페이스를 구현한 `DataSourceTransactionManager` 를 사용하면 된다.

`getTransaction()` 메서드를 사용하면 트랜잭션이 시작된다.

<br>

만약 JTA를 이용하는 글로벌 트랜잭션 방식으로 변경하고 싶다면

```
PlatformTransactionManager transactionManager = new JTATransactionManager();
```

위와 같이, 할당되는 구현 클래스만 변경시키면 된다.

  </details>


  <details>

  <summary><h3> DI가 테스트 코드를 짤 때 도움을 주는 이유 </h3></summary>

내부 설계가 복잡한 외부 API를 사용하더라도, 그 API의 추상화된 인터페이스를 상속하는 더미 클래스 파일을 DI 받도록 할 수 있다.

예를 들어, 사용자에게 기존에 있던 로직에 부가기능으로 메일을 보내는 서비스를 추가했고 표준 기술인 `JavaMail`을 사용했다고 가정해보자.

<br>

이 로직의 테스트 코드를 실행시킬때마다, 부가기능인 메일 전송이 계속 실행되는 것이 맞을까?

메일 발송 기능은 매우 부하가 큰 작업이고 메일 발송 기능은 보조적인 기능에 불과하다.

<br>

따라서 메일 전송 기능에 대한 인터페이스를 DI 받도록 하고 실제 서비스에서는 구현부가 담겨있는 `MailSender`를 사용하고

테스트 시에는, 따로 만들어 둔 가볍게 동작하는 커스텀 `MailSender` 를 DI 받도록 할 수 있다.

<br>

즉, 간단한 오브젝트의 코드를 테스트하는 데 너무 거창한 작업이 뒤따르는 경우 오브젝트의 변화 없이 다른 클래스를 DI해주는 방식으로 간단하게 해결할 수 있다.

  </details>

</details>



<details>

<summary><h2> Chapter 6. AOP </h2></summary>

<details>

<summary><h3> 테스트 대상 오브젝트를 고립시킨 후 테스트 코드를 작성하는 이유 </h3></summary>


하나의 기능을 테스트하는 것 처럼 보이지만, 의존관계를 갖는 경우 테스트가 진행되는 동안에 같이 실행된다.

의존하는 오브젝트가 DB나 서버에 의존하는 오브젝트라면, 단순한 비즈니스 로직만을 테스트하는 것이 아니게 된다.

<br>

테스트하려는 코드가 아닌, 환경 세팅에 문제가 생기면 테스트가 실패하거나 결과가 달라질 수 있기 때문이다. 그리고 수행 속도도 느려질 수 있다.

<br>

그래서 테스트의 대상이 환경이나, 외부 서버, 다른 클래스의 코드에 종속되고 영향을 받지 않도록 고립시킬 필요가 있다.

  </details>

<details>

<summary><h3> 다이나믹 프록시와 InvocationHandler </h3></summary>

인터페이스를 구현하는 클래스 로직에 부가기능을 추가하고 싶은 경우, 프록시 패턴을 사용할 수 있다.

같은 인터페이스를 구현하면서 해당 인터페이스를 DI받아 기존의 클래스 로직을 실행할 수 있도록 하고, 부가기능을 추가하고 싶다면 추가하는 방식이다.

```java
// 인터페이스
public interface Hello {
    String sayHello(String name);
}

// 기존 로직
public class HelloTarget implements Hello{
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}

// 부가기능
public class HelloUppercase implements Hello {
    private final Hello hello;

    @Override
    public String sayHello(String name) {
        return hello.sayHello(name).toUpperCase();
    }
}
```

위와 같은 방식으로 구현할 수 있다.

이러한 방식이 일반적인 프록시 패턴 방식이다.

이 방식은 부가기능을 추가했을 때, **중복되는 부분이 많이 발생**하고 부가기능이 추가되지 않는 메서드는 단순히 기존 메서드를 호출하는 메서드가 되는데

이는 인터페이스의 규모가 크면 부담스러운 작업이 되고, **타깃 인터페이스의 메서드가 추가되거나 변경될때마다 함께 수정**해줘야 한다는 단점이 존재한다.

이러한 문제를 **다이나믹 프록시**를 사용하면 해결할 수 있고, 리플렉션 API를 이용해서 프록시를 생성한다.

<br>

다이나믹 프록시는 런타임 시 다이나믹하게 만들어지는 오브젝트이다.

다이나믹 프록시로부터 요청을 전달받으려면 `InvocationHandler` 인터페이스를 구현해야한다.

구현해야하는 메서드는 `invoke()` 하나 뿐이고 다이나믹 프록시가 클라이언트로부터 받는 모든 요청은 `invoke()` 메서드로 전달된다.

<br>

```java
public class UppercaseHandler implements InvocationHandler {

    Hello target;

    public UppercaseHandler(Hello target) {
        this.target = target;
    }

    // 형변환 안정성을 위해서는, instanceof 로 체크하는 것이 바람직한 방법
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String ret = (String) method.invoke(target, args);
        return ret.toUpperCase();
    }
}

```

`Hello` 클래스의 내용을 모두 대문자로 만들어주는 부가기능이 담긴 프록시이다.

`invoke()` 메서드 하나로, `Hello` 클래스의 메서드 개수와 상관없이 부가기능을 처리할 수 있다.

<br>

```java
    @Test
    public void test() {
        Hello helloProxy = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(new HelloTarget())
        );
    }
```

위와 같이, 클래스로더와 어떤 인터페이스의 프록시를 만들것인지, 어떤 `InvocationHandler` 구현 클래스를 사용할 것인지 파라미터로 설정하면 된다.
  </details>


<details>

<summary><h3> 다이나믹 프록시 DI 방법과 FactoryBean </h3></summary>

`InvocationHandler` 를 implements하는 클래스를 정의하고, `Proxy.newProxyInstance()`와 같은 메서드를 통해서 다이나믹 프록시를 생성했다.

그리고 생성한 다이나믹 프록시에 있는 메서드를 사용하면, `@Override` 한 `invoke()` 메서드에 정의된 대로 로직이 진행된다.

`method.invoke(target,args)`로 타깃의 메서드를 먼저 사용하고 후처리를 한다거나

`method.getName()`으로 메서드의 이름으로 필터링 한 뒤, 추가한 부가기능을 실행하고 `method.invoke(target,args)` 로 타깃의 메서드를 실행할 수 도 있다.

<br>

어쨌든, 이러한 다이나믹 프록시를 사용하기 위해서는 **DI를 이용해야하는데** 일반적으로 DI는 리플렉션 API를 통해 클래스의 이름과 프로퍼티로 등록하지만

다이나믹 프록시의 특성 상, 파라미터를 통해 어**떤 인터페이스의 프록시를 만들것인지 결정**되므로 빈에 등록할 수 없다.

<br>

따라서, 빈으로 등록하기 위해서는 `FactoryBean` 인터페이스를 구현해야한다.

<br>

```java
@Setter
public class ProxyFactoryBean implements FactoryBean<Object> {
    Object target;
    Class<?> serviceInterface;

    @Override
    public Object getObject() throws Exception {
        CustomHandler customHandler = new CustomHandler(target); //InvocationHandler를 구현한 클래스
        return Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{serviceInterface},
                customHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}

```

위와 같이 `FactoryBean` 인터페이스를 구현한다.

`getObjectType` 는 `getObject()` 메서드가 반환하는 객체의 타입을 정확하게 알려주기 위해 사용된다.

이를 통해 DI(Dependency Injection) 컨테이너가 팩토리 빈이 생성하는 객체의 타입을 알 수 있고, 필요한 의존성 주입을 수행할 수 있다.

<br>

`getObject`는 `InvocationHandler`를 구현하여 부가 기능을 추가한 프록시 객체를 반환하도록 되어 있다.

이 팩토리 빈이 생성해주는 `serviceInterface` 타입의 메서드를 사용하면 `InvocationHandler`의 `invoke()` 메서드에 정의된 대로 실행이 될 것이다.

<br>

```java
@Configuration
public class AppConfig {
    @Bean
    public CustomService myBean() throws Exception {
        return (CustomService)ProxyFactoryBean().getObject();
    }

    @Bean
    public ProxyFactoryBean proxyFactoryBean() {
        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setServiceInterface(CustomServiceInterface.class);
        factoryBean.setTarget(CustomService.class);
        return factoryBean;
    }
}
```

위와 같이 `@Config` 클래스를 설정해주면

`ProxyFactoryBean` 클래스는 내부적으로 `CustomServiceInterface`를 상속받아 구현부가 담겨있는 `CustomService`의 메서드에 `InvocationHandler` 를 이용해 부가기능을 추가한 다이나믹 프록시 객체를 반환하는 `getObject()` 메서드가 있는 클래스를 반환하게 된다.

<br>

위 설정파일 상, myBean 이라는 이름으로 등록된 빈을 사용하면, 다이나믹 프록시 객체를 사용하게 되고 원하는 부가기능이 담긴 객체가 된다.

  </details>


<details>

<summary><h3> 스프링이 제공하는 ProxyBeanFactory와 Advisor </h3></summary>

기존 방식은 메서드 단위로 부가기능을 한번에 제공하는 것은 가능했지만, 여러 개의 클래스에 공통적으로 부가기능을 제공하는 것은 불가능했다.

스프링은 프록시 오브젝트를 생성해주는 기술을 추상화한 팩토리 빈을 제공해준다.

<br>

`ProxyBeanFactory` 는 프록시를 생성해서 **빈 오브젝트로 등록하게 해주는 팩토리 빈**이다.

**순수하게 프록시를 생성하는 작업만**을 담당하고 프록시를 통해 제공해줄 **부가기능은 별도의 빈에 둘 수 있다.**

<br>

부가기능은 `InvocationHandler` 대신에 `MethodInterceptor` 인터페이스를 구현해서 만든다.

`MethodInterceptor` 의 `invoke` 메서드는 부가기능을 적용할 대상을 클래스가 알고 있는 것이 아니라 제공받기 때문에

**타겟 오브젝트와 상관없이 독립적으로 만들어 질 수 있고, 따라서 싱글톤으로 등록 가능**하다.

<br>

또한, 타겟 오브젝트가 구현하고 있는 인터페이스의 정보를 자동 검출 기능을 활용해 정보를 알아낸다.

<br>

부가기능을 적용해야할 대상은 `Pointcut` 인터페이스를 구현해서 만든 오브젝트를 제공해주면 된다.

<br>

그리고 `PointCut`을 통해 적용 대상을 필터링하고 싶다면 `PointCut` 과 `MethodInterceptor` 를 묶어서 제공해야한다.

`ProxyBeanFactory`는 여러개의 포인트컷과 어드바이스(부가기능)가 등록될 수 있기 때문에, 혼란을 방지하기 위함이다.

그리고, 이렇게 **포인트컷(부가기능이 적용될 대상을 판별)과 어드바이스(부가기능)이 합쳐진 것을 어드바이저**라고 한다.

</details>

<details>

<summary><h3> 빈 후처리기와 포인트컷 클래스</h3></summary>

빈 후처리기는 `BeanPostProcessor` 인터페이스를 구현해서 만들 수 있다.

빈 후처리기를 빈으로서 등록하면, 빈 오브젝트가 생성될 때 마다 빈 후처리기에 보내서 후처리 작업을 요청한다.

`DefaultAdvisorAutoProxyCreator` 빈 후처리기가 등록되어 있으면, 빈 오브젝트를 만들 때 마다 후처리기에 빈을 보낸다.

전달받은 빈이 프록시 적용 대상인지 확인하고, 적용 대상이라면 프록시 생성기에게 현재 빈에 대한 프록시를 만들게 하고 만들어진 프록시에 어드바이저를 연결해준다.

최종적으로 빈에 등록되는 것은 프록시 객체가 등록이 된다.

<br>

클라이언트가 프록시 객체로 등록된 빈을 사용하게 되면, 해당 프록시 객체의 PointCut으로 부가기능 적용대상인지 판단하고 원본 객체의 메서드에 부가기능을 추가해서 실행하게 된다.

<br>

결과적으로 빈 후처리기를 빈으로 등록하고, 클래스 혹은 메서드가 프록시 대상인지 판별할 수 있는 Pointcut 을 빈으로 등록해주면,

모든 빈을 등록할 때, 포인트컷에 의해 프록시 객체가 될지 안될지를 판단하게 된다.

```java
public class NameMatchClassMethodPointcut extends NameMatchMethodPointcut {
    public void setMappedClassName(String mappedClassName) {
        this.setClassFilter(new SimpleClassFilter(mappedClassName));
        
    }
    static class SimpleClassFilter implements ClassFilter{
        String mappedName;

        private SimpleClassFilter(String mappedName) {
            this.mappedName = mappedName;
                    
        }

        @Override
        public boolean matches(Class<?> clazz) {
            return PatternMatchUtils.simpleMatch(mappedName, clazz.getSimpleName());
        }
    }
}
```

위와 같은 포인트 컷을 빈으로 등록해주고 `setMappedName()`으로 메서드 이름 패턴을, `setMappedClassName()`으로 클래스 이름 패턴 정보를 제공하면 된다.


</details>


<details>

<summary><h3> 포인트 컷 표현식</h3></summary>

`AspectJExpressionPointcut` 클래스를 사용하면 포인트컷 표현식을 사용할 수 있다.

`AspectJ` 라는 유명한 프레임워크에서 제공하는 것을 가져와 문법을 확장해서 사용하는 것이다.

그래서 **AspectJ 포인트컷 표현식** 이라고도 한다.

```java
package com.practice.toby.ch6.pointcut;

public class Target implements TargetInterface{
    @Override
    public int minus(int a, int b) throws RuntimeException {
        return 0;
    }
}

```

위와 같은 경로에 `Target` 클래스가 있고 파라미터가 `(int a, int b)` 인 `minus` 메서드가 있을 때,

`"execution(public int com.practice.toby.ch6.pointcut.Target.minus(int, int) throws java.lang.RuntimeException)"` 와 같은 표현식으로 나타낼 수 있다.

<br>

```
execution([접근제한자 패턴] 리턴타입패턴 [패키지클래스이름패턴.]메서드이름패턴 (타입패턴 | "..",...))
```

`[]` 는 옵션 항목이다.

리턴 타입을 `*` 로 표현하면 와일드 카드로서 모든 타입 혹은 모든 메서드명이 적용된다.

파라미터의 개수와 타입을 무시하려면 `..` 를 넣어주면 된다.

<br>

따라서, `"execution(* *(..))"` 로 표현하면, 모든 리턴타입과 메서드 명을 허용하고 파라미터의 타입과 개수를 신경쓰지 않는 포인트컷이 된다.



</details>


<details>

<summary><h3> 트랜잭션 전파 (propagation)</h3></summary>


트랜잭션 안에서 실행되는 로직이, 다른 메서드를 호출할 때, 이미 존재하는 트랜잭션이 그 메서드에 끼치는 영향이라고 볼 수 있다.

1️⃣ **PROPAGATION_REQUIRED**

진행 중인 트랜잭션이 없으면 새로 시작하고, 이미 시작된 트랜잭션이 있으면 이에 참여한다.

2️⃣ **PROPAGATION_REQUIRES_NEW**

항상 새로운 트랜잭션을 시작해 독자적으로 동작한다.

3️⃣ **PROPAGATION_NOT_SUPPORTED**

진행 중인 트랜잭션이 있어도 무시하고 트랜잭션 없이 동작하게 한다.

</details>

</details>
