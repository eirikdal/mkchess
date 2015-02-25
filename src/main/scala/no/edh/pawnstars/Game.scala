package no.edh.pawnstars

import akka.actor.{ActorRef, Props, ActorSystem}
import no.edh.pawnstars.game.{Square, Board}
import no.edh.pawnstars.pieces._

import scala.collection.immutable.HashMap
import scala.util.Random

object BoardBuilder {
  val system = ActorSystem("ChessGame")

  def makeBoard(in: List[String]) : Board = {
    require(in.length == 8, "Initialization board incorrect length, please try again")
    val inPieces = for(i <- 0 until 8; j <- 0 until 8) yield readSquare(in(i)(j), Square(i, j))
    val startBoard = inPieces.map {
      case (Some(p: ActorRef), sq: Square) => (sq, Some(p))
      case (None, sq: Square) => (sq, None)
    }
    new Board(new HashMap[Square, Option[ActorRef]]() ++ startBoard)
  }

  implicit def indexPos(i: Int) : Square = Square(i >> 8, i & 7)

  private def readSquare(in: Char, p: Square) : (Option[ActorRef], Square) = {
    val c = if(in.isUpper) White else Black
    (in.toUpper match {
      case 'P' => Some(system.actorOf(Props(classOf[Pawn], c(), p), in + String.valueOf(new Random().nextInt())))
      case 'N' => Some(system.actorOf(Props(classOf[Knight], c(), p), in + String.valueOf(new Random().nextInt())))
      case 'B' => Some(system.actorOf(Props(classOf[Bishop], c(), p), in + String.valueOf(new Random().nextInt())))
      case 'R' => Some(system.actorOf(Props(classOf[Rook], c(), p), in + String.valueOf(new Random().nextInt())))
      case 'Q' => Some(system.actorOf(Props(classOf[Queen], c(), p), in + String.valueOf(new Random().nextInt())))
      case 'K' => Some(system.actorOf(Props(classOf[King], c(), p), in + String.valueOf(new Random().nextInt())))
      case  _  => None
    }, p)
  }
}

class Game() {

  def _b = List(
    "rnbqkbnr",
    "pppppppp",
    "........",
    "........",
    "........",
    "........",
    "PPPPPPPP",
    "RNBQKBNR")

  val board = BoardBuilder.makeBoard(_b)
}
