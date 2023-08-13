import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import RefrigeratorModal from './RefrigeratorModal';
import { dateFormatter } from '../tools/Date';

export const Refrigerator = () => {
  const [items, setItems] = useState([]);
  const [open, setOpen] = useState(false);
  const [selectedItem, setSelectedItem] = useState(null);

  const getItems = async () => {
    const response = await axios.get('http://192.168.0.2:15000/tomato/v1/refrigerator/');
    setItems(response.data.map((item) => ({ ...item })));
  };

  const deleteItem = async (item) => {
    const confirmDelete = window.confirm("本当に削除しますか？");
    if (confirmDelete) {
      await axios.delete(`http://192.168.0.2:15000/tomato/v1/refrigerator?id=${item.id}`);
      getItems();
    }
  };

  useEffect(() => {
    getItems();
  }, []);

  const openNewItemModal = () => {
    setSelectedItem(null);
    setOpen(true);
  };

  const openEditItemModal = (item) => {
    setSelectedItem(item);
    setOpen(true);
  };

  return (
    <TableContainer>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>名称</TableCell>
            <TableCell align="right">数量</TableCell>
            <TableCell align="right">期限</TableCell>
            <TableCell align="right">編集</TableCell>
            <TableCell align="right">削除</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {items.map((item) => (
            <TableRow key={item.id}>
              <TableCell>{item.name}</TableCell>
              <TableCell align="right">{item.quantity}</TableCell>
              <TableCell align="right">{dateFormatter(item.expiry)}</TableCell>
              <TableCell align="right" style={{width: "80px"}}>
                <Button color={"warning"} onClick={() => openEditItemModal(item)} startIcon={<EditIcon />}>
                  編集
                </Button>
              </TableCell>
              <TableCell align="right" style={{width: "80px"}}>
                <Button color={"error"} onClick={() => deleteItem(item)} startIcon={<DeleteIcon />}>
                  削除
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <Button onClick={openNewItemModal} color="success" variant="contained">
        新規追加
      </Button>
      <RefrigeratorModal open={open} setOpen={setOpen} getItems={getItems} selectedItem={selectedItem} setSelectedItem={setSelectedItem} />
    </TableContainer>
  );
}

