package com.talentmind.procapi.counter

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import com.talentmind.procapi.counter.Counter._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class CounterSpec
    extends ScalaTestWithActorTestKit
    with Matchers
    with AnyWordSpecLike {
  "Sending a GetCounter message" should {
    "Reply with an GetCounterResponse containing the state of the CounterActor" in {
      val replyProbe = createTestProbe[GetCounterResponse]
      val testCounter = spawn(Counter())

      testCounter ! GetCounter(replyProbe.ref)
      replyProbe.expectMessage(GetCounterResponse(0))
    }
  }

  "Sending an Increment message" should {
    "Reply with a an ActionPerformed message with correct description" in {
      val replyProbe = createTestProbe[ActionPerformed]
      val testCounter = spawn(Counter())
      testCounter ! Increment(replyProbe.ref)
      replyProbe.expectMessage(ActionPerformed("Counter incremented by one"))
    }

    "increment it's counter state to 1" in {
      val replyProbe = createTestProbe[Response]
      val testCounter = spawn(Counter())
      testCounter ! Increment(replyProbe.ref)
      replyProbe.expectMessage(ActionPerformed("Counter incremented by one"))

      testCounter ! GetCounter(replyProbe.ref)
      replyProbe.expectMessage(GetCounterResponse(1))
    }
  }

  "Sending a Decrement message" should {
    "Reply with a an ActionPerformed message with correct description" in {
      val replyProbe = createTestProbe[Response]
      val testCounter = spawn(Counter())
      testCounter ! Decrement(replyProbe.ref)
      replyProbe.expectMessage(ActionPerformed("Counter decremented by one"))
    }

    "result negative counter when decrementing from initial state" in {
      val replyProbe = createTestProbe[Response]
      val testCounter = spawn(Counter())
      testCounter ! Decrement(replyProbe.ref)
      replyProbe.expectMessage(ActionPerformed("Counter decremented by one"))

      testCounter ! GetCounter(replyProbe.ref)
      replyProbe.expectMessage(GetCounterResponse(-1))
    }
  }

  "Sending a SetValue message" should {
    "Set the counter state to the value in the message" in {
      val replyProbe = createTestProbe[Response]
      val testCounter = spawn(Counter())

      testCounter ! SetValue(100, replyProbe.ref)
      ActionPerformed(s"Counter value set at 100")
    }
  }

  "Sending a ClearCounter message" should {
    "Set the counter state to zero" in {
      val replyProbe = createTestProbe[Response]
      val testCounter = spawn(Counter())
      testCounter ! ClearCounter(replyProbe.ref)
      replyProbe.expectMessage(ActionPerformed("Counter reset to zero"))

      testCounter ! GetCounter(replyProbe.ref)
      replyProbe.expectMessage(GetCounterResponse(0))
    }
  }

}
