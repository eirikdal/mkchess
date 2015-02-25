package no.edh.pawnstars.pieces

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.event.Logging
import no.edh.pawnstars.game.Square

case class Knight(color: Color, square: Square) extends Piece {
  override def receive: Receive = {
    case _ => sender ! false
  }
}

case class Queen(color: Color, square: Square) extends Piece {
  override def receive: Receive = {
    case _ => sender ! false
  }
}

case class Bishop(color: Color, square: Square) extends Piece {
  override def receive: Receive = {
    case _ => sender ! false
  }
}

case class Rook(color: Color, square: Square) extends Piece {
  override def receive: Receive = {
    case Is(t) => sender ! false
  }
}

case class King(color: Color, square: Square) extends Piece {
  override def receive: Receive = {
    case _ => sender ! false
  }
}

case class Pawn(color: Color, square: Square) extends Piece {
  val log = Logging(context.system, this)

  final val value = 1

  override def receive: Receive = {
    case Is(t: Pawn.type) => sender ! true
  }
}


