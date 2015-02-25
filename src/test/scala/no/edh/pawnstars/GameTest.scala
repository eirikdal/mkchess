package no.edh.pawnstars

import akka.actor.Actor.Receive
import akka.actor.{ActorSystem, Props, Actor, ActorRef}
import no.edh.pawnstars.game.Square
import no.edh.pawnstars.pieces._
import org.scalatest.junit.JUnitRunner
import org.scalatest._
import akka.testkit.{TestKit, ImplicitSender, TestActorRef}
import scala.concurrent.duration._
import scala.concurrent.{Future, Await}
import akka.pattern.ask

import scala.collection.mutable

class GameTest extends TestKit(ActorSystem("testSystem")) with FlatSpecLike with Matchers {

  "A new game " should "start with pawns on ranks 2 and 7" in {
    val game = new Game()

  }

  "A new game " should "have a board with 16 pieces" in {
    val game = new Game()

    game.board.pieces().size should be (16)
  }

  "A new game " should "start with 2 black rooks and 2 white rooks" in {

  }

  "A new game " should "start with 2 black knights and 2 white knights" in {

  }

  "A new game " should "start with 2 black bishops and 2 white bishops" in {

  }

  "A new game " should "be able to move piece " in {
    val game = new Game()

    game.board.move("e6")

    val board = game.board.get()
    board.get(Square(0, 0)) should be(0)
  }

  "A new game " should "start with 8 black pawns and 8 white pawns" in {
    var ps = 0
    val actorRef = TestActorRef(new Actor {
      def receive = {
        case true => sender ! 1
        case false => sender ! 0
      }
    })

    val game = new Game()
    game.board.pieces() map {
      case (sq: Square, Some(p: ActorRef)) =>
        val future = p.ask(Is(Pawn))(1000 second)
        val i = if (Await.result(future, 1000 second).toString.toBoolean) 1 else 0
        ps = ps + i
      case _ => false
    }
    ps should be(16)
  }
}
