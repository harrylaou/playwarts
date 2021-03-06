import org.wartremover.test.WartTestTraverser
import org.danielnixon.playwarts.PlayGlobalExecutionContext
import org.scalatest.FunSuite

class PlayGlobalExecutionContextTest extends FunSuite {

  test("can't use Play's global execution context") {
    val result = WartTestTraverser(PlayGlobalExecutionContext) {
      val foo = play.api.libs.concurrent.Execution.defaultContext
    }
    assertResult(List("Execution is disabled - declare a dependency on an ExecutionContext instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("can't use Play's global implicit execution context") {
    val result = WartTestTraverser(PlayGlobalExecutionContext) {
      val foo = play.api.libs.concurrent.Execution.Implicits
    }
    assertResult(List("Execution is disabled - declare a dependency on an ExecutionContext instead"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }

  test("PlayGlobalExecutionContext wart obeys SuppressWarnings") {
    val result = WartTestTraverser(PlayGlobalExecutionContext) {
      @SuppressWarnings(Array("org.danielnixon.playwarts.PlayGlobalExecutionContext"))
      val foo = scala.concurrent.ExecutionContext.global
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
}
