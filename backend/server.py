from flask import Flask, request, jsonify
from flask_restful import Resource, Api
from sqlalchemy import create_engine
from json import dumps
from flask_restplus import Api

db_connect = create_engine('sqlite:///tagline.db')
app = Flask(__name__)
api = Api(app)

apiDoc = Api(version='1.0', title='TagLine API',
          description='Projeto TagLine Hack.GOV.PE')


ns_car = apiDoc.namespace('car', description='Informações do carro')

@ns_car.route('/')
class Car(Resource):
    def get(self):
        conn = db_connect.connect()
        query = conn.execute("select * from car")
        result = [dict(zip(tuple(query.keys()), i)) for i in query.cursor]
        return jsonify(result)
    

ns_bike = apiDoc.namespace('bike', description='Informações do bike')
@ns_bike.route('/')
class Bike(Resource):
    def get(self):
        conn = db_connect.connect()
        query = conn.execute("select * from bike")
        result = [dict(zip(tuple(query.keys()), i)) for i in query.cursor]
        return jsonify(result)

    def post(self):
        conn = db_connect.connect()
        id_bike = request.json['id_bike']
        longi = request.json['longi']
        lati = request.json['lati']

        conn.execute(
            "insert into bike values('{0}','{1}', '{2}')".format(id_bike, longi, lati))

        query = conn.execute('select * from bike order by id desc limit 1')
        result = [dict(zip(tuple(query.keys()), i)) for i in query.cursor]
        return jsonify(result)

    def put(self):
        conn = db_connect.connect()
        
        id_bike = request.json['id_bike']
        longi = request.json['longi']
        lati = request.json['lati']

        conn.execute("update bike set longi ='" + str(longi) +
                     "', lati ='" + str(lati) + "'  where id =%d " % int(id_bike))

        query = conn.execute("select * from bike where id=%d " % int(id_bike))
        result = [dict(zip(tuple(query.keys()), i)) for i in query.cursor]
        return jsonify(result)

@ns_bike.route('/<int:id>')
class BikeById(Resource):
    def delete(self, id_bike):
        conn = db_connect.connect()
        conn.execute("delete from bike where id_bike=%d " % int(id_bike))
        return {"status": "success"}

    def get(self, id):
        conn = db_connect.connect()
        query = conn.execute("select * from bike where id_bike =%d " % int(id_bike))
        result = [dict(zip(tuple(query.keys()), i)) for i in query.cursor]
        return jsonify(result)


api.add_resource(Bike, '/bike') 
api.add_resource(BikeById, '/bike/<id>') 
api.add_resource(Car, '/car') 

if __name__ == '__main__':
    app.run()

