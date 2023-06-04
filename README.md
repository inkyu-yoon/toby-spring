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

  </details>