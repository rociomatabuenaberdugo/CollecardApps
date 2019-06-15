import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service'
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  userList: User[];

  constructor(private userService: UserService, private toastr: ToastrService) { }

  ngOnInit() {
    this.userService.getUsers()
    .snapshotChanges()
    .subscribe(item => {
      this.userList = [];
      item.forEach(element => {
        let relleno = element.payload.toJSON();
        relleno['$key'] = element.key;
        this.userList.push(relleno as User);
      });
    });
  }

  onRemoveCRUD($key: string) {
    if(confirm("¿Esta seguro de querer eliminar al usuario?")) {
      this.userService.deleteUser($key);
      this.toastr.success('Eliminación completada', 'Se ha eliminado el usuario correctamente.');
    }
  }

}
