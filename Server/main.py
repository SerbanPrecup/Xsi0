from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///joc.db'

db = SQLAlchemy(app)


def verificare_castig(caracter, e11, e12, e13, e21, e22, e23, e31, e32, e33):
    if (e11 == caracter and e12 == caracter and e13 == caracter) or \
            (e11 == caracter and e22 == caracter and e33 == caracter) or \
            (e11 == caracter and e21 == caracter and e31 == caracter) or \
            (e12 == caracter and e22 == caracter and e32 == caracter) or \
            (e13 == caracter and e23 == caracter and e33 == caracter) or \
            (e13 == caracter and e22 == caracter and e31 == caracter) or \
            (e21 == caracter and e22 == caracter and e23 == caracter) or \
            (e31 == caracter and e32 == caracter and e33 == caracter):
        return True
    else:
        return False


def verificare_joc_terminat(e11, e12, e13, e21, e22, e23, e31, e32, e33):
    if e11 != " " and e12 != " " and e13 != " " and \
            e21 != " " and e22 != " " and e23 != " " and \
            e31 != " " and e32 != " " and e33 != " ":
        return True
    else:
        return False


class Tabel(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    room = db.Column(db.String, unique=True)
    user1 = db.Column(db.String)
    user2 = db.Column(db.String)
    e11 = db.Column(db.String(1))
    e12 = db.Column(db.String(1))
    e13 = db.Column(db.String(1))
    e21 = db.Column(db.String(1))
    e22 = db.Column(db.String(1))
    e23 = db.Column(db.String(1))
    e31 = db.Column(db.String(1))
    e32 = db.Column(db.String(1))
    e33 = db.Column(db.String(1))
    turnPlayer1 = db.Column(db.Boolean)


with app.app_context():
    db.create_all()



@app.route("/get_users", methods=["POST"])
def get_users():
    request_data = request.get_json()
    room = request_data.get("room")

    room_entry = Tabel.query.filter_by(room=room).first()
    user1 = room_entry.user1
    user2 = room_entry.user2

    if room_entry:
        return jsonify({"USER1": user1, "USER2": user2})
    else:
        return jsonify({"RESPONSE": "TRUE"})


@app.route("/creare_room", methods=["POST"])
def creare_room():
    request_data = request.get_json()
    room = request_data.get("room")
    user1 = request_data.get("user1")

    existing_entry = Tabel.query.filter_by(room=room).first()
    if existing_entry:
        return jsonify({"RESPONSE": "FALSE"})

    new_entry = Tabel(user1=user1, room=room, e11=" ", e12=" ", e13=" ", e21=" ", e22=" ", e23=" ", e31=" ", e32=" ",
                      e33=" ", turnPlayer1=True)

    db.session.add(new_entry)
    db.session.commit()

    return jsonify({"RESPONSE": "TRUE"})


@app.route("/join_room", methods=["POST"])
def join_room():
    request_data = request.get_json()
    room = request_data.get("room")
    user2 = request_data.get("user2")

    existing_entry = Tabel.query.filter_by(room=room).first()

    if existing_entry:
        if existing_entry.user2:
            return jsonify({"RESPONSE": "FALSE"})
        else:
            existing_entry.user2 = user2
            db.session.commit()
            return jsonify({"RESPONSE": "TRUE"})
    else:
        return jsonify({"RESPONSE": "FALSE"})


@app.route("/get_matrice", methods=["POST"])
def get_matrice():
    request1 = request.get_json()
    room = request1.get("room")

    entry = Tabel.query.filter_by(room=room).first()

    e11 = entry.e11
    e12 = entry.e12
    e13 = entry.e13
    e21 = entry.e21
    e22 = entry.e22
    e23 = entry.e23
    e31 = entry.e31
    e32 = entry.e32
    e33 = entry.e33
    turnPlayer1 = entry.turnPlayer1

    if verificare_castig("X", entry.e11, entry.e12, entry.e13, entry.e21, entry.e22, entry.e23, entry.e31,
                                 entry.e32, entry.e33):
                return jsonify({"RESPONSE": "X a castigat"})
    elif verificare_castig("0", entry.e11, entry.e12, entry.e13, entry.e21, entry.e22, entry.e23, entry.e31,
                                 entry.e32, entry.e33):
                return jsonify({"RESPONSE": "0 a castigat"})
    elif verificare_joc_terminat(entry.e11, entry.e12, entry.e13, entry.e21, entry.e22, entry.e23, entry.e31,
                                         entry.e32, entry.e33):
                return jsonify({"RESPONSE": "EGALITATE"})

    if entry:
        return jsonify(
            {"e11": e11, "e12": e12, "e13": e13, "e21": e21, "e22": e22, "e23": e23, "e31": e31, "e32": e32, "e33": e33,
             "turnPlayer1": turnPlayer1, "RESPONSE": "FALSE"})
    else:
        return jsonify({"RESPONSE": "TRUE"})


@app.route("/put_x", methods=["POST"])
def handle_x():
    try:
        request1 = request.get_json()
        i = request1.get("i")
        j = request1.get("j")
        room = request1.get("room")

        entry = Tabel.query.filter_by(room=room).first()
        if entry and entry.turnPlayer1:
            if i == "1" and j == "1":
                if entry.e11 == " ":
                    entry.e11 = "X"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "1" and j == "2":
                if entry.e12 == " ":
                    entry.e12 = "X"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "1" and j == "3":
                if entry.e13 == " ":
                    entry.e13 = "X"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "2" and j == "1":
                if entry.e21 == " ":
                    entry.e21 = "X"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "2" and j == "2":
                if entry.e22 == " ":
                    entry.e22 = "X"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "2" and j == "3":
                if entry.e23 == " ":
                    entry.e23 = "X"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "3" and j == "1":
                if entry.e31 == " ":
                    entry.e31 = "X"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "3" and j == "2":
                if entry.e32 == " ":
                    entry.e32 = "X"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "3" and j == "3":
                if entry.e33 == " ":
                    entry.e33 = "X"
                else:
                    return jsonify({"RESPONSE": "FALSE"})

            entry.turnPlayer1 = False
            db.session.commit()


            return jsonify({"RESPONSE": "TRUE"})
        else:
            return jsonify({"RESPONSE": "FALS"})

    except Exception as e:
        return jsonify({"RESPONSE": str(e)})


@app.route("/put_0", methods=["POST"])
def handle_0():
    try:
        request_data = request.get_json()

        i = request_data.get("i")
        j = request_data.get("j")
        room = request_data.get("room")

        entry = Tabel.query.filter_by(room=room).first()

        if entry and entry.turnPlayer1 == False:
            if i == "1" and j == "1":
                if entry.e11 == " ":
                    entry.e11 = "0"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "1" and j == "2":
                if entry.e12 == " ":
                    entry.e12 = "0"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "1" and j == "3":
                if entry.e13 == " ":
                    entry.e13 = "0"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "2" and j == "1":
                if entry.e21 == " ":
                    entry.e21 = "0"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "2" and j == "2":
                if entry.e22 == " ":
                    entry.e22 = "0"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "2" and j == "3":
                if entry.e23 == " ":
                    entry.e23 = "0"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "3" and j == "1":
                if entry.e31 == " ":
                    entry.e31 = "0"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "3" and j == "2":
                if entry.e32 == " ":
                    entry.e32 = "0"
                else:
                    return jsonify({"RESPONSE": "FALSE"})
            elif i == "3" and j == "3":
                if entry.e33 == " ":
                    entry.e33 = "0"
                else:
                    return jsonify({"RESPONSE": "FALSE"})

            entry.turnPlayer1 = True
            db.session.commit()
            return jsonify({"RESPONSE": "TRUE"})
        else:
            return jsonify({"RESPONSE": "FALS"})
    except Exception as e:
        return jsonify({"RESPONSE": f"{str(e)}"})


@app.route("/sterge_room", methods=["POST"])
def sterge_room():
    request_data = request.get_json()
    room = request_data.get("room")
    entry = Tabel.query.filter_by(room=room).first()
    if entry:
        db.session.delete(entry)
        db.session.commit()
        return jsonify({"RESPONSE": "TRUE"})
    else:
        return jsonify({"RESPONSE": "FALSE"})


app.run(host='0.0.0.0')
