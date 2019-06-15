import { Injectable } from '@angular/core';

import { AngularFireDatabase, AngularFireList } from 'angularfire2/database';
import { User } from '../models/user'

@Injectable({
  providedIn: 'root'
})
export class UserService {

  userList: AngularFireList<any>;
  selectUser: User = new User(); 

  constructor(private firebase: AngularFireDatabase) { }

  getUsers() {
    return this.userList = this.firebase.list('usuario');
  }

  insertUser(usuario: User) {
    this.userList.push({
      nombre: usuario.nombre,
      apellido: usuario.apellido,
      email: usuario.email,
      telefono: usuario.telefono,
      usuario: usuario.usuario
    })
  }

  updateUser(user: User) {
    this.userList.update(user.$key, {
      nombre: user.nombre,
      apellido: user.apellido,
      email: user.email,
      telefono: user.telefono,
      usuario: user.usuario
    });
  }

  deleteUser($key: string) {
    this.userList.remove($key);
  }

}
