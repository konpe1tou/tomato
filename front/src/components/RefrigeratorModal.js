import React from 'react';
import { TextField, Dialog, DialogActions, Button, DialogContent, DialogTitle } from '@mui/material';
import axios from 'axios';

function RefrigeratorModal({ open, setOpen, getItems, selectedItem, setSelectedItem }) {
  const handleClose = () => {
    setOpen(false);
    setSelectedItem(null);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    let item = {
      name: e.target.name.value,
      quantity: e.target.quantity.value,
      expiry: e.target.expiry.value + "T00:00:00.000",
    };

    if (selectedItem) {
      item = {...item, id: selectedItem.id}
      await axios.put(`http://localhost:15000/tomato/v1/refrigerator/`, item);
    } else {
      await axios.post('http://localhost:15000/tomato/v1/refrigerator/', item);
    }

    getItems();
    handleClose();
  };

  if (!open) {
    return <></>
  }
  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>{selectedItem ? '編集' : '新規追加'}</DialogTitle>
      <DialogContent>
        <form onSubmit={handleSubmit}>
          <TextField
            autoFocus
            margin="dense"
            id="name"
            label="名称"
            type="text"
            fullWidth
            variant="outlined"
            defaultValue={selectedItem ? selectedItem.name : ''}
          />
          <TextField
            autoFocus
            margin="dense"
            id="quantity"
            label="数量"
            type="number"
            fullWidth
            variant="outlined"
            inputProps={{ min: 0 }}
            defaultValue={selectedItem ? selectedItem.quantity : ''}
          />
          <TextField
            id="expiry"
            label="期限"
            type="date"
            defaultValue={selectedItem ? selectedItem.expiry.split('T')[0] : ''}
            InputLabelProps={{
              shrink: true,
            }}
          />
          <DialogActions>
            <Button onClick={handleClose}>キャンセル</Button>
            <Button type="submit">{selectedItem ? '更新' : '追加'}</Button>
          </DialogActions>
        </form>
      </DialogContent>
    </Dialog>
  );
}

export default RefrigeratorModal;
